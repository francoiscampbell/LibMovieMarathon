package io.github.francoiscampbell;

import com.google.gson.GsonBuilder;
import io.github.francoiscampbell.api.ApiKey;
import io.github.francoiscampbell.api.Request;
import io.github.francoiscampbell.apimodel.ApiMovie;
import io.github.francoiscampbell.apimodel.ApiShowtime;
import io.github.francoiscampbell.apimodel.ApiTheatre;
import io.github.francoiscampbell.collections.SelfMap;
import io.github.francoiscampbell.gson.DateTimeConverter;
import io.github.francoiscampbell.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by francois on 15-07-02.
 */
public class Main {
    private static final String API_URL = "http://data.tmsapi.com";

    private SelfMap<ApiTheatre> allTheatres;
    private List<ApiMovie> allMovies;

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
            List<ApiMovie> desiredMovies = selectMovies(allMovies);
            for (ApiTheatre t : allTheatres) {
                if (t.getMoviesPlayingHere().containsAll(desiredMovies)) {
                    List<Schedule> possibleSchedules = new ArrayList<>();
                    Deque<ApiShowtime> currentPermutation = new LinkedList<>();
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
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DateTime.class, new DateTimeConverter());

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(builder.create()))
                .build();

        String currentDate = LocalDate.now()
                .toString();
        Request request = new Request.Builder(currentDate).endpoint(restAdapter)
                .apiKey(ApiKey.API_KEY)
                .postcode("M5T1N5")
//                .radiusUnit(Request.RadiusUnit.KM)
                .build();
//        List<ApiMovie> apiMovies = request.execute();
        allMovies = request.execute();
        List<ApiTheatre> allTheatres = reorganizeMoviesIntoModel();
        sortShowtimes(allTheatres);
    }

    /**
     * Reorganizes the response from the Gracenote API from a movies->theatres->showtimes
     * to theatres->showtimes->movies format
     */
    private List<ApiTheatre> reorganizeMoviesIntoModel() {
//        List<ApiMovie> allMovies = new ArrayList<>();
        for (ApiMovie apiMovie : allMovies) {
            if (apiMovie.getRunTime() == null) {
                continue; //null runtime events can't be planned (usually theatre events, etc)
            }
            for (ApiShowtime apiShowtime : apiMovie.getApiShowtimes()) {
                apiShowtime.setMovie(apiMovie);
                ApiTheatre apiTheatre = allTheatres.putIfAbsent(apiShowtime.getApiTheatre());
                apiTheatre.getShowtimes().add(apiShowtime);

                apiShowtime.setApiTheatre(null);
            }
            apiMovie.setApiShowtimes(null);
        }
        return allTheatres.asList();
    }

    private void sortShowtimes(List<ApiTheatre> theatres) {
        for (ApiTheatre t : theatres) {
//            Collections.sort(t.getShowtimes());
        }
    }

    private List<ApiMovie> selectMovies(List<ApiMovie> movies) {
        System.out.println("Select movie: ");
        int i = 0;
        for (ApiMovie m : movies){
            i++;
            System.out.println("\t" + i + ") " + m.getTitle());

        }
        List<ApiMovie> desiredMovies = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        String selections = s.nextLine();
        String[] selectionsArray = selections.split(",");
        for (String sel : selectionsArray) {
            int selection = Integer.parseInt(sel);
            desiredMovies.add(movies.get(selection));
        }
        return desiredMovies;
    }

    private void generateSchedule(ApiTheatre theatre, List<ApiMovie> movies, DateTime startTime, List<Schedule> possibleSchedules, Deque<ApiShowtime> currentPermutation) {
        if (movies.size() == 0) {
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
            Duration minDelay = Collections.min(delays.values());
            Duration maxDelay = Collections.max(delays.values());
            Duration difference = maxDelay.minus(minDelay);

            float[] redHsb = Color.RGBtoHSB(255, 0, 0, null);
            float[] greenHsb = Color.RGBtoHSB(0, 255, 0, null);

            for (ApiShowtime showtime : schedule.getShowtimes()) {
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
