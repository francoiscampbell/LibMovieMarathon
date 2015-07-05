package io.github.francoiscampbell;

import io.github.francoiscampbell.api.MovieApi;
import io.github.francoiscampbell.apimodel.ApiMovie;
import io.github.francoiscampbell.apimodel.ApiShowtime;
import io.github.francoiscampbell.model.Movie;
import io.github.francoiscampbell.model.Showtime;
import io.github.francoiscampbell.model.Theatre;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
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
    private List<Theatre> theatres;

    public Main() {
        theatres = new ArrayList<>();
        getMovies();
        sortAllShowtimes();
        do {
            Theatre theatre = selectTheatre(theatres);
            List<Movie> movies = selectMovies(theatre);

            List<Queue<Showtime>> schedule = new ArrayList<>();
            Deque<Showtime> currentPermutation = new LinkedList<>();
            generateSchedule(theatre, movies, new LocalDateTime(0), schedule, currentPermutation);
            printSchedule(schedule);
        } while (!quit());
    }

    private boolean quit() {
        System.out.println("Type 'q' to quit");
        return new Scanner(System.in).next()
                                     .startsWith("q");
    }

    private void printSchedule(List<Queue<Showtime>> schedule) {
        System.out.println(schedule.size() + " schedules generated:");
        int i = 0;
        for (Queue<Showtime> showtimes : schedule) {
            i++;
            System.out.println("Schedule " + i + ":");
            for (Showtime showtime : showtimes) {
                System.out.println("\t" + showtime.getTime() + " " + showtime.getMovie()
                                                                             .getTitle());
            }
        }
    }


    public void getMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

        MovieApi api = restAdapter.create(MovieApi.class);

        String currentDate = LocalDate.now()
                                      .toString();
        String postcode = "M5T 1N5";
        String apiKey = "xv4za7trkge9yrz4b4h6ws9s";

        final CountDownLatch cdl = new CountDownLatch(1);

        api.getMovies(currentDate, postcode, apiKey, new Callback<List<ApiMovie>>() {
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
            for (ApiShowtime apiShowtime : apiMovie.getApiShowtimes()) {
                //if the theatre is in the list, get it
                //if it's not, add a new theatre to the list
                Theatre theatre = new Theatre(apiShowtime.getApiTheatre());
                try {
                    theatre = theatres.get(theatres.indexOf(theatre));
                } catch (IndexOutOfBoundsException e) {
                    theatres.add(theatre);
                }

                Showtime showtime = new Showtime(apiShowtime, movie);
                theatre.getShowtimes()
                       .add(showtime);
            }
        }
    }

    private void sortAllShowtimes() {
        for (Theatre t : theatres) {
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

    private List<Movie> selectMovies(Theatre theatre) {
        System.out.println("Select movie: ");
        List<Showtime> showtimes = theatre.getShowtimes();
        Set<Movie> listedMovies = new HashSet<>();
        for (int i = 0; i < showtimes.size(); i++) {
            Showtime showtime = showtimes.get(i);
            Movie m = showtime.getMovie();
            if (!listedMovies.contains(m)) {
                System.out.println("\t" + i + ") " + m.getTitle());
                listedMovies.add(m);
            }
        }
        List<Movie> desiredMovies = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        String selections = s.nextLine();
        String[] selectionsArray = selections.split(",");
        for (String sel : selectionsArray) {
            int selection = Integer.parseInt(sel);
            desiredMovies.add(showtimes.get(selection)
                                       .getMovie());
        }
        return desiredMovies;
    }

    private void generateSchedule(Theatre theatre, List<Movie> movies, LocalDateTime startTime, List<Queue<Showtime>> schedule, Deque<Showtime> currentPermutation) {
        if (movies.size() == 0) {
            schedule.add(new LinkedList<>(currentPermutation));
            return;
        }
        for (Movie movie : movies) {
            Showtime showtime;
            LocalDateTime nextAvailableStartTime = startTime;
            while ((showtime = findNextShowtimeForMovie(theatre, movie, nextAvailableStartTime)) != null) {
                currentPermutation.add(showtime);
                List<Movie> remainingMovies = new ArrayList<>(movies);
                remainingMovies.remove(movie);
                nextAvailableStartTime = showtime.getDateTime()
                                                 .plus(movie.getTotalLength());
                generateSchedule(theatre, remainingMovies, nextAvailableStartTime, schedule, currentPermutation);
                currentPermutation.removeLast();
            }
        }

    }

    private Showtime findNextShowtimeForMovie(Theatre theatre, Movie movie, LocalDateTime startTime) {
        for (Showtime showtime : theatre.getShowtimes()) {
            LocalDateTime dateTime = showtime.getDateTime();
            if (showtime.getMovie()
                        .equals(movie) && dateTime.isAfter(startTime)) {
                return showtime;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        new Main();
    }
}
