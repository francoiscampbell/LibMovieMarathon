package io.github.francoiscampbell;

import io.github.francoiscampbell.api.ApiKey;
import io.github.francoiscampbell.api.OnConnectApiRequest;
import io.github.francoiscampbell.apimodel.Movie;
import io.github.francoiscampbell.apimodel.Showtime;
import io.github.francoiscampbell.apimodel.Theatre;
import io.github.francoiscampbell.collections.SelfMap;
import io.github.francoiscampbell.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by francois on 15-07-02.
 */
public class Main {


    /**
     * Main
     * This class is a placeholder for a real application. It's just used to develop the logic
     * TODO: Refactor to make this a proper application
     */
    public Main() {
    }

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        List<Movie> allMovies = getMovies();

        mainLoop(allMovies);
    }

    private void mainLoop(List<Movie> allMovies) {
        List<Theatre> allTheatres = reorganizeMoviesIntoModel(allMovies);

        do {
            List<Movie> desiredMovies = selectMovies(allMovies);
            List<Theatre> possibleTheatres = calculatePossibleTheatres(allTheatres, desiredMovies);

            List<Schedule> possibleSchedules = new ArrayList<>();
            Deque<Showtime> currentPermutation = new LinkedList<>();
            for (Theatre t : possibleTheatres) {
                generateSchedule(t, desiredMovies, new DateTime(0), possibleSchedules, currentPermutation);
            }
            sortSchedulesByDelay(possibleSchedules);
            printSchedules(possibleSchedules);
        } while (!quit());
    }

    private boolean quit() {
        System.out.println("Type 'q' to quit");
        return new Scanner(System.in).next()
                                     .startsWith("q");
    }

    /**
     * Reorganizes the response from the Gracenote API from a movies->theatres->showtimes
     * to theatres->showtimes->movies format
     *
     * @param allMovies The list of movies to choose from
     */
    private List<Theatre> reorganizeMoviesIntoModel(List<Movie> allMovies) {
        SelfMap<Theatre> allTheatres = new SelfMap<>();

        for (Movie movie : allMovies) {
            for (Showtime showtime : movie.getShowtimes()) {
                showtime.setMovie(movie); //set the movie to be a child of the showtime
                Theatre theatre = allTheatres.putIfAbsent(showtime
                        .getTheatre()); //reduce identical object duplication by getting a copy
                theatre.addShowtime(showtime); //add the showtime to the showtimes for this theatre

                showtime.setTheatre(null); //remove the theatre child from the showtime
            }
            movie.setShowtimes(null); //remove the showtimes child from the movie
        }
        for (Theatre t : allTheatres) {
            Collections.sort(t.getShowtimes());
        }
        return allTheatres.asList();
    }

    private void sortSchedulesByDelay(List<Schedule> possibleSchedules) {
        Collections.sort(possibleSchedules, (o1, o2) -> o1.getTotalDelay().compareTo(o2.getTotalDelay()));
    }

    private List<Theatre> calculatePossibleTheatres(List<Theatre> allTheatres, List<Movie> desiredMovies) {
        List<Theatre> possibleTheatres = new LinkedList<>();
        for (Theatre t : allTheatres) {
            if (t.getMoviesPlayingHere().containsAll(desiredMovies)) {
                possibleTheatres.add(t);
            }
        }
        return possibleTheatres;
    }

    private List<Movie> getMovies() {
        String currentDate = LocalDate.now().toString();
        OnConnectApiRequest request = new OnConnectApiRequest.Builder(currentDate)
                .apiKey(ApiKey.API_KEY)
                .postcode("M5T1N5")
//                .radiusUnit(OnConnectApiRequest.RadiusUnit.KM)
                .build();

        List<Movie> allMovies = request.execute();
        removeUnplannableMovies(allMovies);
        return allMovies;
    }

    private void removeUnplannableMovies(List<Movie> allMovies) {
        for (Iterator<Movie> iterator = allMovies.iterator(); iterator.hasNext(); ) {
            if (iterator.next().getRunTime() == null) {
                iterator.remove();
            }
        }
    }

    private List<Movie> selectMovies(List<Movie> movies) {
        System.out.println("Select movie: ");
        int i = 0;
        for (Movie m : movies) {
            i++;
            System.out.println("\t" + i + ") " + m.getTitle());

        }

        List<Movie> desiredMovies = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        String selections = s.nextLine();
        String[] selectionsArray = selections.split(",");
        for (String sel : selectionsArray) {
            int selection = Integer.parseInt(sel) - 1; //off by one error
            desiredMovies.add(movies.get(selection));
        }
        return desiredMovies;
    }

    private void generateSchedule(Theatre theatre, List<Movie> movies, DateTime startTime, List<Schedule> possibleSchedules, Deque<Showtime> currentPermutation) {
        if (movies.size() == 0) { //end condition for recursive algorithm
            possibleSchedules.add(new Schedule(currentPermutation, theatre));
            return;
        }
        for (Movie movie : movies) {
            Showtime showtime;
            DateTime nextAvailableStartTime = startTime;
            while ((showtime = findNextShowtimeForMovie(theatre, movie, nextAvailableStartTime)) != null) {
                currentPermutation.add(showtime);
                List<Movie> remainingMovies = new ArrayList<>(movies);
                remainingMovies.remove(movie);
                nextAvailableStartTime = showtime.getStartDateTime()
                                                 .plus(movie.getTotalLength());
                generateSchedule(theatre, remainingMovies, nextAvailableStartTime, possibleSchedules, currentPermutation);
                currentPermutation.removeLast();
            }
        }
    }

    private Showtime findNextShowtimeForMovie(Theatre theatre, Movie movie, DateTime startTime) {
        for (Showtime showtime : theatre.getShowtimes()) {
            DateTime dateTime = showtime.getStartDateTime();
            if (showtime.getMovie()
                        .equals(movie) && dateTime.isAfter(startTime)) {
                return showtime;
            }
        }
        return null;
    }

    private void printSchedules(List<Schedule> possibleSchedules) {
        System.out.println(possibleSchedules.size() + " schedules generated:");

        int i = 0;
        for (Schedule schedule : possibleSchedules) {
            i++;
            System.out.println("Schedule " + i + " at " + schedule.getTheatre()
                                                                  .getName() + ":");

            Map<Showtime, Duration> delays = schedule.getDelays();
            Duration minDelay;
            Duration maxDelay;
            Duration difference;
            if (delays.size() > 0) {
                minDelay = Collections.min(delays.values());
                maxDelay = Collections.max(delays.values());
            } else {
                minDelay = new Duration(0);
                maxDelay = new Duration(0);
            }
            difference = maxDelay.minus(minDelay);

            for (Showtime showtime : schedule.getShowtimes()) {
                System.out.println("\t" + showtime.toFriendlyString());
                Duration delay = schedule.getDelayAfterShowtime(showtime);
                if (delay != null) {
                    float ratio = MoreMath.protectedDivide(delay.minus(minDelay)
                                                                .getMillis(), difference.getMillis(), 1);

                    Color lerp = colorLerp(Color.GREEN, Color.RED, ratio);
//                    System.out.println("lerp = " + lerp);
                    System.out.println("\tDelay of " + delay.getStandardMinutes() + " minutes");
                }
            }
        }
    }

    private Color colorLerp(Color min, Color max, float ratio) {
        float[] minHsb = Color.RGBtoHSB(min.getRed(), min.getGreen(), min.getBlue(), null);
        float[] maxHsb = Color.RGBtoHSB(max.getRed(), max.getGreen(), max.getBlue(), null);
        float[] hsb = hsbLerp(minHsb, maxHsb, ratio);
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    private float[] hsbLerp(float[] minHsb, float[] maxHsb, float ratio) {
        float inverseRatio = 1 - ratio;
        float h = minHsb[0] * ratio + maxHsb[0] * inverseRatio;
        float s = minHsb[1] * ratio + maxHsb[1] * inverseRatio;
        float b = minHsb[2] * ratio + maxHsb[2] * inverseRatio;
        return new float[]{h, s, b};
    }
}
