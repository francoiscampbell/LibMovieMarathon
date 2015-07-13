package io.github.francoiscampbell;

import io.github.francoiscampbell.api.Request;
import io.github.francoiscampbell.apimodel.ApiMovie;
import io.github.francoiscampbell.apimodel.ApiShowtime;
import io.github.francoiscampbell.collections.SelfMap;
import io.github.francoiscampbell.model.Movie;
import io.github.francoiscampbell.model.Schedule;
import io.github.francoiscampbell.model.Showtime;
import io.github.francoiscampbell.model.Theatre;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import retrofit.RestAdapter;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by francois on 15-07-02.
 */
public class Main {
    private static final String API_URL = "http://data.tmsapi.com";
    private static final String API_KEY = "xv4za7trkge9yrz4b4h6ws9s";
    private SelfMap<Theatre> allTheatres;
    private List<Movie> allMovies;

    /**
     * Main
     * This class is a placeholder for a real application. It's just used to develop the logic
     * TODO: Refactor to make this a proper application
     */
    public Main() {
        allTheatres = new SelfMap<>();
        allMovies = new ArrayList<>();
    }

    public void start() {
        getMovies();
        mainLoop();
    }

    private void mainLoop() {
        do {
            List<Movie> desiredMovies = selectMovies(allMovies);
            for (Theatre t : allTheatres) {
                if (t.getMoviesPlayingHere().containsAll(desiredMovies)) {
                    List<Schedule> possibleSchedules = new ArrayList<>();
                    Deque<Showtime> currentPermutation = new LinkedList<>();
                    generateSchedule(t, desiredMovies, new DateTime(0), possibleSchedules, currentPermutation);
                    printSchedules(possibleSchedules);
                }
            }
        } while (!quit());
    }


    private boolean quit() {
        System.out.println("Type 'q' to quit");
        return new Scanner(System.in).next()
                                     .startsWith("q");
    }

    private void getMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

        String currentDate = LocalDate.now()
                                      .toString();
        Request request = new Request.Builder(currentDate).endpoint(restAdapter)
                                                          .apiKey(API_KEY)
                                                          .postcode("M5T 1N5")
                                                          .radiusUnit(Request.RadiusUnit.KM)
                                                          .build();
        List<ApiMovie> apiMovies = request.execute();
        reorganizeMoviesIntoModel(apiMovies);
        sortShowtimes();
    }

    /**
     * Reorganizes the response from the Gracenote API from a movies->theatres->showtimes
     * to theatres->showtimes->movies format
     *
     * @param apiMovieList The list of Movies as returned by the Gracenote API, converted by GSON
     */
    private void reorganizeMoviesIntoModel(List<ApiMovie> apiMovieList) {
        for (ApiMovie apiMovie : apiMovieList) {
            if (apiMovie.getRunTime() == null) {
                continue; //null runtime events can't be planned (usually theatre events, etc)
            }
            Movie movie = new Movie(apiMovie);
            allMovies.add(movie);
            for (ApiShowtime apiShowtime : apiMovie.getApiShowtimes()) {
                Theatre theatre = allTheatres.putIfAbsent(new Theatre(apiShowtime.getApiTheatre()));
                Showtime showtime = new Showtime(apiShowtime, movie);
                theatre.getShowtimes()
                       .add(showtime);
            }
        }
    }

    private void sortShowtimes() {
        for (Theatre t : allTheatres) {
            Collections.sort(t.getShowtimes());
        }
    }

    private List<Movie> selectMovies(List<Movie> movies) {
        System.out.println("Select movie: ");
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            System.out.println("\t" + i + ") " + m.getTitle());
        }
        List<Movie> desiredMovies = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        String selections = s.nextLine();
        String[] selectionsArray = selections.split(",");
        for (String sel : selectionsArray) {
            int selection = Integer.parseInt(sel);
            desiredMovies.add(movies.get(selection));
        }
        return desiredMovies;
    }

    private void generateSchedule(Theatre theatre, List<Movie> movies, DateTime startTime, List<Schedule> possibleSchedules, Deque<Showtime> currentPermutation) {
        if (movies.size() == 0) {
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
            Duration minDelay = Collections.min(delays.values());
            Duration maxDelay = Collections.max(delays.values());
            Duration difference = maxDelay.minus(minDelay);

            float[] redHsb = Color.RGBtoHSB(255, 0, 0, null);
            float[] greenHsb = Color.RGBtoHSB(0, 255, 0, null);

            for (Showtime showtime : schedule.getShowtimes()) {
                System.out.println("\t" + showtime.toFriendlyString());
                Duration delay = schedule.getDelayAfterShowtime(showtime);
                if (delay != null) {
                    float ratio = MoreMath.protectedDivide(delay.minus(minDelay)
                                                                .getMillis(), difference.getMillis(), 1);
                    float inverseRatio = 1 - ratio;
                    float h = greenHsb[0] * ratio + redHsb[0] * inverseRatio;
                    float s = greenHsb[1] * ratio + redHsb[1] * inverseRatio;
                    float v = greenHsb[2] * ratio + redHsb[2] * inverseRatio;
                    Color lerp = Color.getHSBColor(h, s, v);
                    System.out.println("lerp = " + lerp);
                    System.out.println("\tDelay of " + delay.getStandardMinutes() + " minutes");
                }
            }
        }
    }


    public static void main(String[] args) {
        new Main().start();
    }
}
