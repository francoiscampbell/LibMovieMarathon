package io.github.francoiscampbell;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.francoiscampbell.api.ApiKey;
import io.github.francoiscampbell.api.Request;
import io.github.francoiscampbell.apimodel.ApiMovie;
import io.github.francoiscampbell.apimodel.ApiShowtime;
import io.github.francoiscampbell.apimodel.ApiTheatre;
import io.github.francoiscampbell.collections.SelfMap;
import io.github.francoiscampbell.gson.DateTimeConverter;
import io.github.francoiscampbell.gson.DurationConverter;
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
            List<Schedule> possibleSchedules = new ArrayList<>();
            Deque<ApiShowtime> currentPermutation = new LinkedList<>();
            for (ApiTheatre t : allTheatres) {
                if (t.getMoviesPlayingHere().containsAll(desiredMovies)) {
                    generateSchedule(t, desiredMovies, new DateTime(0), possibleSchedules, currentPermutation);
                }
            }
            Collections.sort(possibleSchedules, (o1, o2) -> o1.getTotalDelay().compareTo(o2.getTotalDelay()));
            printSchedules(possibleSchedules);
        } while (!quit());
    }


    private boolean quit() {
        System.out.println("Type 'q' to quit");
        return new Scanner(System.in).next()
                                     .startsWith("q");
    }

    private void getMovies() {
        Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeConverter())
                                     .registerTypeAdapter(Duration.class, new DurationConverter())
                                     .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        String currentDate = LocalDate.now().toString();
        Request request = new Request.Builder(currentDate).endpoint(restAdapter)
                                                          .apiKey(ApiKey.API_KEY)
                .postcode("M5T1N5")
//                .radiusUnit(Request.RadiusUnit.KM)
                .build();

        allMovies = request.execute();
        reorganizeMoviesIntoModel();
        sortShowtimes();
    }

    /**
     * Reorganizes the response from the Gracenote API from a movies->theatres->showtimes
     * to theatres->showtimes->movies format
     */
    private void reorganizeMoviesIntoModel() {
        for (Iterator<ApiMovie> iterator = allMovies.iterator(); iterator.hasNext(); ) {
            ApiMovie apiMovie = iterator.next();
            if (apiMovie.getRunTime() == null) {
                iterator.remove(); //null runtime events can't be planned (usually theatre events, etc)
            }
            for (ApiShowtime apiShowtime : apiMovie.getApiShowtimes()) {
                apiShowtime.setMovie(apiMovie); //set the movie to be a child of the showtime
                ApiTheatre apiTheatre = allTheatres.putIfAbsent(apiShowtime
                        .getApiTheatre()); //reduce identical object duplication by getting the copy if it exists in the SelfMap of theratres
                apiTheatre.getShowtimes().add(apiShowtime); //add the showtime to the showtimes for this theatre

                apiShowtime.setApiTheatre(null); //remove the theatre child from the showtime
            }
            apiMovie.setApiShowtimes(null); //remove the showtimes child from the movie
        }
    }

    private void sortShowtimes() {
        for (ApiTheatre t : allTheatres) {
            Collections.sort(t.getShowtimes());
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
//                    System.out.println("lerp = " + lerp);
                    System.out.println("\tDelay of " + delay.getStandardMinutes() + " minutes");
                }
            }
        }
    }


    public static void main(String[] args) {
        new Main().start();
    }
}
