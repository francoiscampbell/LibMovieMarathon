package io.github.francoiscampbell;

import io.github.francoiscampbell.api.MovieApi;
import io.github.francoiscampbell.apimodel.ApiMovie;
import io.github.francoiscampbell.apimodel.ApiShowtime;
import io.github.francoiscampbell.model.Movie;
import io.github.francoiscampbell.model.Schedule;
import io.github.francoiscampbell.model.Showtime;
import io.github.francoiscampbell.model.Theatre;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by francois on 15-07-02.
 */
public class Main {
    private static final String API_URL = "http://data.tmsapi.com";
    private static final String API_KM = "km";
    private static final String API_MILES = "mi";
    private static final String API_KEY = "xv4za7trkge9yrz4b4h6ws9s";
    private List<Theatre> allTheatres;
    private List<Movie> allMovies;

    public Main() {
        allTheatres = new ArrayList<>();
        allMovies = new ArrayList<>();
    }

    public void start() {
        getMovies();
        sortAllShowtimes();
        mainLoop();
    }

    private void mainLoop() {
        do {
            List<Movie> desiredMovies = selectMoviesFromList(allMovies);
            for (Theatre t : allTheatres) {
                if (t.getMoviesPlayingHere()
                     .containsAll(desiredMovies)) {
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

    private void printSchedules(List<Schedule> possibleSchedules) {
        System.out.println(possibleSchedules.size() + " schedules generated:");
        int i = 0;
        for (Schedule schedule : possibleSchedules) {
            i++;
            System.out.println("Schedule " + i + " at " + schedule.getTheatre()
                                                                  .getName() + ":");
            for (Showtime showtime : schedule.getShowtimes()) {
                System.out.println("\t" + showtime.toFriendlyString());
                Duration delay = schedule.getDelayAfterShowtime(showtime);
                if (delay != null) {
                    System.out.println("\tDelay of " + delay.getStandardMinutes() + " minutes");
                }
            }
        }
    }


    private void getMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

        MovieApi api = restAdapter.create(MovieApi.class);

        String currentDate = LocalDate.now()
                                      .toString();
        String postcode = "M5T 1N5";


        final CountDownLatch cdl = new CountDownLatch(1);

        api.getMovies(
                currentDate, //date
                null, //num days (default 1 if null)
                postcode,
                null, //latitude (not needed if using postcode)
                null, //longitude (not needed if using postcode)
                null, //radius (default 5 if null)
                API_KM, //radius unit (default miles if null)
                API_KEY,
                new Callback<List<ApiMovie>>() {
                    @Override
                    public void success(List<ApiMovie> apiMovieList, Response response) {
                        reorganizeMovies(apiMovieList);
                        cdl.countDown();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println("error = " + error);
                    }
                });

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Reorganizes the response from the Gracenote API from a movies->theatres->showtimes
     * to theatres->showtimes format
     *
     * @param apiMovieList The list of Movies as returned by the Gracenote API, converted by GSON
     */
    private void reorganizeMovies(List<ApiMovie> apiMovieList) {
        for (ApiMovie apiMovie : apiMovieList) {
            if (apiMovie.getRunTime() == null) {
                break; //null runtime events can't be planned (usually theatre events, etc)
            }
            Movie movie = new Movie(apiMovie);
            allMovies.add(movie);
            for (ApiShowtime apiShowtime : apiMovie.getApiShowtimes()) {
                //if the theatre is in the list, get it
                //if it's not, add a new theatre to the list
                Theatre theatre = new Theatre(apiShowtime.getApiTheatre());
                try {
                    theatre = allTheatres.get(allTheatres.indexOf(theatre));
                } catch (IndexOutOfBoundsException e) {
                    allTheatres.add(theatre);
                }

                Showtime showtime = new Showtime(apiShowtime, movie);
                theatre.getShowtimes()
                       .add(showtime);
            }
        }
    }

    private void sortAllShowtimes() {
        for (Theatre t : allTheatres) {
            sortShowtimes(t);
        }
    }

    private void sortShowtimes(Theatre theatre) {
        Collections.sort(theatre.getShowtimes());
    }

    private Theatre selectTheatre(List<Theatre> theatres) {
        System.out.println("Select theatre: ");
        for (int i = 0; i < theatres.size(); i++) {
            Theatre t = theatres.get(i);
            System.out.println("\t" + i + ") " + t.getName());
        }
        Scanner s = new Scanner(System.in);
        int selection = s.nextInt();
        return theatres.get(selection);
    }

    private List<Movie> selectMoviesFromList(List<Movie> movies) {
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


    private List<Movie> selectMoviesFromTheatre(Theatre theatre) {
        return selectMoviesFromList(theatre.getMoviesPlayingHere());
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


    public static void main(String[] args) {
        new Main().start();
    }
}
