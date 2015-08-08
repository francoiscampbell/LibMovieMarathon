package io.github.francoiscampbell;

import io.github.francoiscampbell.api.*;
import io.github.francoiscampbell.apimodel.*;
import io.github.francoiscampbell.model.*;
import org.joda.time.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Main
 * This class is a placeholder for a real application. It's just used to develop the logic
 * TODO: Refactor to make this a proper application
 */
public class Main {
    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        ScheduleGenerator.Builder builder = getScheduleGenarator();
        mainLoop(builder);
    }

    private void mainLoop(ScheduleGenerator.Builder builder) {
        do {
            chooseParameters(builder);
            ScheduleGenerator generator = builder.build();
            List<Movie> desiredMovies = selectMovies(generator.getAllMovies());
            List<Schedule> possibleSchedules = generator.generateSchedules(desiredMovies);
            printSchedules(possibleSchedules);
        } while (!quit());
    }

    private void chooseParameters(ScheduleGenerator.Builder builder) {
        builder.sortByDelay(true)
               .ignorePreviews(false)
               .maxOverlap(Duration.standardMinutes(0));
    }

    private boolean quit() {
        System.out.println("Type 'q' to quit");
        return new Scanner(System.in).next()
                                     .startsWith("q");
    }

    private ScheduleGenerator.Builder getScheduleGenarator() {
        String currentDate = LocalDate.now().toString();
        OnConnectApiRequest request = new OnConnectApiRequest.Builder(currentDate)
                .apiKey(ApiKey.API_KEY)
                .postcode("M5T 1N5")
                .radiusUnit(OnConnectApiRequest.RadiusUnit.KM)
//                .logLevel(RestAdapter.LogLevel.FULL)
//                .mockResponse(new File("mockResponse.json"))
                .build();

        return request.execute();
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
                System.out.println("\t" + showtime.toFriendlyString(false));
                Duration delay = schedule.getDelayAfterShowtime(showtime);
                if (delay != null) {
                    float ratio = MoreMath.protectedDivide(delay.minus(minDelay)
                                                                .getMillis(), difference.getMillis(), 1);

                    Color lerp = colorLerp(Color.GREEN, Color.RED, ratio);
//                    System.out.println("lerp = " + lerp);
                    String plural = delay.getStandardMinutes() == 1 ? " minute" : " minutes";
                    System.out.println("\tDelay of " + delay.getStandardMinutes() + plural);
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
