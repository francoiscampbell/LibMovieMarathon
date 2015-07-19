package io.github.francoiscampbell;

import io.github.francoiscampbell.api.ApiKey;
import io.github.francoiscampbell.api.OnConnectApiRequest;
import io.github.francoiscampbell.apimodel.ApiMovie;
import io.github.francoiscampbell.apimodel.ApiShowtime;
import io.github.francoiscampbell.apimodel.ApiTheatre;
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
        List<ApiMovie> allMovies = getMovies();

        mainLoop(allMovies);
    }

    private void mainLoop(List<ApiMovie> allMovies) {
        List<ApiTheatre> allTheatres = reorganizeMoviesIntoModel(allMovies);

        do {
            List<ApiMovie> desiredMovies = selectMovies(allMovies);
            List<ApiTheatre> possibleTheatres = calculatePossibleTheatres(allTheatres, desiredMovies);

            List<Schedule> possibleSchedules = new ArrayList<>();
            Deque<ApiShowtime> currentPermutation = new LinkedList<>();
            for (ApiTheatre t : possibleTheatres) {
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
    private List<ApiTheatre> reorganizeMoviesIntoModel(List<ApiMovie> allMovies) {
        SelfMap<ApiTheatre> allTheatres = new SelfMap<>();

        for (ApiMovie apiMovie : allMovies) {
            for (ApiShowtime apiShowtime : apiMovie.getApiShowtimes()) {
                apiShowtime.setMovie(apiMovie); //set the movie to be a child of the showtime
                ApiTheatre apiTheatre = allTheatres.putIfAbsent(apiShowtime
                        .getApiTheatre()); //reduce identical object duplication by getting a copy
                apiTheatre.addShowtime(apiShowtime); //add the showtime to the showtimes for this theatre

                apiShowtime.setApiTheatre(null); //remove the theatre child from the showtime
            }
            apiMovie.setApiShowtimes(null); //remove the showtimes child from the movie
        }
        for (ApiTheatre t : allTheatres) {
            Collections.sort(t.getShowtimes());
        }
        return allTheatres.asList();
    }

    private void sortSchedulesByDelay(List<Schedule> possibleSchedules) {
        Collections.sort(possibleSchedules, (o1, o2) -> o1.getTotalDelay().compareTo(o2.getTotalDelay()));
    }

    private List<ApiTheatre> calculatePossibleTheatres(List<ApiTheatre> allTheatres, List<ApiMovie> desiredMovies) {
        List<ApiTheatre> possibleTheatres = new LinkedList<>();
        for (ApiTheatre t : allTheatres) {
            if (t.getMoviesPlayingHere().containsAll(desiredMovies)) {
                possibleTheatres.add(t);
            }
        }
        return possibleTheatres;
    }

    private List<ApiMovie> getMovies() {
        String currentDate = LocalDate.now().toString();
        OnConnectApiRequest request = new OnConnectApiRequest.Builder(currentDate)
                .apiKey(ApiKey.API_KEY)
                .postcode("M5T1N5")
//                .radiusUnit(OnConnectApiRequest.RadiusUnit.KM)
                .build();

        List<ApiMovie> allMovies = request.execute();
        removeUnplannableMovies(allMovies);
        return allMovies;
    }

    private void removeUnplannableMovies(List<ApiMovie> allMovies) {
        for (Iterator<ApiMovie> iterator = allMovies.iterator(); iterator.hasNext(); ) {
            if (iterator.next().getRunTime() == null) {
                iterator.remove();
            }
        }
    }

    private List<ApiMovie> selectMovies(List<ApiMovie> movies) {
        System.out.println("Select movie: ");
        int i = 0;
        for (ApiMovie m : movies) {
            i++;
            System.out.println("\t" + i + ") " + m.getTitle());

        }

        List<ApiMovie> desiredMovies = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        String selections = s.nextLine();
        String[] selectionsArray = selections.split(",");
        for (String sel : selectionsArray) {
            int selection = Integer.parseInt(sel) - 1; //off by one error
            desiredMovies.add(movies.get(selection));
        }
        return desiredMovies;
    }

    private void generateSchedule(ApiTheatre theatre, List<ApiMovie> movies, DateTime startTime, List<Schedule> possibleSchedules, Deque<ApiShowtime> currentPermutation) {
        if (movies.size() == 0) { //end condition for recursive algorithm
            possibleSchedules.add(new Schedule(currentPermutation, theatre));
            return;
        }
        for (ApiMovie movie : movies) {
            ApiShowtime showtime;
            DateTime nextAvailableStartTime = startTime;
            while ((showtime = findNextShowtimeForMovie(theatre, movie, nextAvailableStartTime)) != null) {
                currentPermutation.add(showtime);
                List<ApiMovie> remainingMovies = new ArrayList<>(movies);
                remainingMovies.remove(movie);
                nextAvailableStartTime = showtime.getStartDateTime()
                                                 .plus(movie.getTotalLength());
                generateSchedule(theatre, remainingMovies, nextAvailableStartTime, possibleSchedules, currentPermutation);
                currentPermutation.removeLast();
            }
        }
    }

    private ApiShowtime findNextShowtimeForMovie(ApiTheatre theatre, ApiMovie movie, DateTime startTime) {
        for (ApiShowtime showtime : theatre.getShowtimes()) {
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

            Map<ApiShowtime, Duration> delays = schedule.getDelays();
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

            for (ApiShowtime showtime : schedule.getShowtimes()) {
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
