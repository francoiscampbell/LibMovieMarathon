package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.Movie;
import io.github.francoiscampbell.apimodel.Showtime;
import io.github.francoiscampbell.apimodel.Theatre;
import io.github.francoiscampbell.collections.SelfMap;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.*;

/**
 * Created by francois on 15-07-19.
 */
public class ScheduleGenerator {
    private List<Theatre> allTheatres;

    private List<Movie> desiredMovies;
    private boolean sortByDelay;
    private boolean includePreviewsLength;
    private DateTime earliestTime;
    private DateTime latestTime;
    private Duration maxIndividualDelay;
    private Duration maxTotalDelay;
    private Duration maxOverlap;


    public List<Schedule> generateSchedules() {
        List<Theatre> possibleTheatres = calculatePossibleTheatres(allTheatres, desiredMovies);
        List<Schedule> possibleSchedules = new ArrayList<>();
        Deque<Showtime> currentPermutation = new LinkedList<>();
        for (Theatre t : possibleTheatres) {
            generateSchedule(t, desiredMovies, earliestTime, possibleSchedules, currentPermutation);
        }
        if (sortByDelay) {
            sortSchedulesByDelay(possibleSchedules);
        }
        return possibleSchedules;
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

    private void generateSchedule(Theatre theatre, List<Movie> movies, DateTime startTime, List<Schedule> possibleSchedules, Deque<Showtime> currentPermutation) {
        if (movies.size() == 0) { //end condition for recursive algorithm
            Schedule currentSchedule = new Schedule(currentPermutation, theatre, includePreviewsLength);
            if (validateSchedule(currentSchedule)) {
                possibleSchedules.add(currentSchedule);
            }
            return;
        }
        for (Movie movie : movies) {
            Showtime showtime;
            DateTime nextAvailableStartTime = startTime;
            while ((showtime = findNextShowtimeForMovie(theatre, movie, nextAvailableStartTime)) != null) {
                currentPermutation.add(showtime);
                List<Movie> remainingMovies = new ArrayList<>(movies);
                remainingMovies.remove(movie);
                nextAvailableStartTime = showtime.getStartDateTime(includePreviewsLength)
                                                 .plus(movie.getRunTime());
                generateSchedule(theatre, remainingMovies, nextAvailableStartTime, possibleSchedules, currentPermutation);
                currentPermutation.removeLast();
            }
        }
    }

    private boolean validateSchedule(Schedule schedule) {
        if (maxTotalDelay != null && schedule.getTotalDelay().isLongerThan(maxTotalDelay)) {
            return false;
        }
        if (maxIndividualDelay != null && schedule.getDelays().size() > 0
                && Collections.max(schedule.getDelays().values()).isLongerThan(maxIndividualDelay)) {
            return false;
        }
        return true;
    }

    private Showtime findNextShowtimeForMovie(Theatre theatre, Movie movie, DateTime startTime) {
        for (Showtime showtime : theatre.getShowtimes()) {
            if (showtime.getMovie().equals(movie)
                    && validateShowtime(showtime, startTime)) {
                return showtime;
            }
        }
        return null;
    }

    private boolean validateShowtime(Showtime showtime, DateTime startTime) {
        if (showtime.getStartDateTime(includePreviewsLength).isBefore(startTime.minus(maxOverlap))) {
            return false;
        }
        if (latestTime != null && showtime.getEndDateTime(includePreviewsLength).isAfter(latestTime)) {
            return false;
        }
        return true;
    }

    public static class Builder {
        private static final int MAX_OVERLAP_MINUTES = 100;
        private ScheduleGenerator scheduleGenerator;

        public Builder(List<Movie> allMovies) {
            scheduleGenerator = new ScheduleGenerator();
            scheduleGenerator.earliestTime = new DateTime(0);

            scheduleGenerator.allTheatres = reorganizeMoviesIntoModel(allMovies);
        }

        public Builder desiredMovies(List<Movie> desiredMovies) {
            scheduleGenerator.desiredMovies = desiredMovies;
            return this;
        }

        public Builder sortByDelay(boolean sortByDelay) {
            scheduleGenerator.sortByDelay = sortByDelay;
            return this;
        }

        public Builder includePreviewsLength(boolean includePreviewsLength) {
            scheduleGenerator.includePreviewsLength = includePreviewsLength;
            return this;
        }

        public Builder earliestTime(DateTime earliestTime) {
            scheduleGenerator.earliestTime = earliestTime;
            return this;
        }

        public Builder latestTime(DateTime latestTime) {
            scheduleGenerator.latestTime = latestTime;
            return this;
        }

        public Builder maxIndividualDelay(Duration maxIndividualDelay) {
            scheduleGenerator.maxIndividualDelay = maxIndividualDelay;
            return this;
        }

        public Builder maxTotalDelay(Duration maxTotalDelay) {
            scheduleGenerator.maxTotalDelay = maxTotalDelay;
            return this;
        }

        public Builder maxOverlap(Duration maxOverlap) {
            if (maxOverlap.getStandardMinutes() > MAX_OVERLAP_MINUTES) {
                maxOverlap = Duration.standardMinutes(MAX_OVERLAP_MINUTES);
            }
            scheduleGenerator.maxOverlap = maxOverlap;
            return this;
        }

        public ScheduleGenerator build() {
            return scheduleGenerator;
        }

        /**
         * Reorganizes the response from the Gracenote API from a movies->theatres->showtimes
         * to theatres->showtimes->movies format
         *
         * @param movies The list of movies to choose from
         */
        private List<Theatre> reorganizeMoviesIntoModel(List<Movie> movies) {
            SelfMap<Theatre> allTheatres = new SelfMap<>();

            for (Movie movie : movies) {
                for (Showtime showtime : movie.getShowtimes()) {
                    showtime.setMovie(movie); //set the movie to be a child of the showtime
                    Theatre theatre = allTheatres.putIfAbsent(showtime
                            .getTheatre()); //reduce identical object duplication by getting a copy
                    theatre.addShowtime(showtime); //add the showtime to the showtimes for this theatre

                    showtime.setTheatre(null); //remove the theatre child from the showtime
                }
                movie.setShowtimes(null); //remove the showtimes child from the movie
            }
            for (Theatre t : allTheatres) { //sort showtimes chronologically
                Collections.sort(t.getShowtimes());
            }
            return allTheatres.asList();
        }
    }
}
