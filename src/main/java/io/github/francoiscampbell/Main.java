package io.github.francoiscampbell;

import io.github.francoiscampbell.api.ApiKey;
import io.github.francoiscampbell.api.OnConnectApiRequest;
import io.github.francoiscampbell.apimodel.Movie;
import io.github.francoiscampbell.apimodel.Showtime;
import io.github.francoiscampbell.model.Schedule;
import io.github.francoiscampbell.model.ScheduleGenerator;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

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
        List<Movie> allMovies = getMovies();
        mainLoop(allMovies);
    }

    private void mainLoop(List<Movie> allMovies) {
        ScheduleGenerator.Builder builder = new ScheduleGenerator.Builder(allMovies);
        do {
            List<Movie> desiredMovies = selectMovies(allMovies);
            builder.desiredMovies(desiredMovies);
            chooseParameters(builder);


            List<Schedule> possibleSchedules = builder.build().generateSchedules();
            printSchedules(possibleSchedules);
        } while (!quit());
    }

    private void chooseParameters(ScheduleGenerator.Builder builder) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sort by delay?");
        boolean sortByDelay = scanner.nextBoolean();

        System.out.println("Include previews length?");
        boolean includePreviewsLength = scanner.nextBoolean();

        System.out.println("Maximum overlap minutes:");
        int maxOverlap = scanner.nextInt();

        System.out.println("Maximum delay minutes between movies:");
        int maxIndividualDelay = scanner.nextInt();

        System.out.println("Maximum total delay minutes:");
        int maxTotalDelay = scanner.nextInt();

        builder.sortByDelay(sortByDelay)
               .includePreviewsLength(includePreviewsLength)
               .maxOverlap(Duration.standardMinutes(maxOverlap))
               .maxIndividualDelay(Duration.standardMinutes(maxIndividualDelay))
               .maxTotalDelay(Duration.standardMinutes(maxTotalDelay));
    }

    private boolean quit() {
        System.out.println("Type 'q' to quit");
        return new Scanner(System.in).next()
                                     .startsWith("q");
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
                System.out.println("\t" + showtime.toFriendlyString(true));
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
