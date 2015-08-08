package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.*;
import io.github.francoiscampbell.collections.*;
import java8.util.stream.*;
import org.joda.time.*;

import java.util.*;

/**
 * Created by francois on 15-07-19.
 */
public class ScheduleGenerator {
    private List<Movie> allMovies;
    private List<Theatre> allTheatres;

    private boolean sortByDelay;
    private boolean ignorePreviews;
    private DateTime earliestTime;
    private DateTime latestTime;
    private Duration maxIndividualDelay;
    private Duration maxTotalDelay;
    private Duration maxOverlap;

    public List<Movie> getAllMovies() {
        return allMovies;
    }

    public List<Schedule> generateSchedules(List<Movie> desiredMovies) {
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
        return StreamSupport.stream(allTheatres)
                            .filter(t -> t.getMoviesPlayingHere().containsAll(desiredMovies))
                            .collect(Collectors.toList());
    }

    private void generateSchedule(Theatre theatre, List<Movie> movies, DateTime startTime, List<Schedule> possibleSchedules, Deque<Showtime> currentPermutation) {
        if (movies.size() == 0 && !currentPermutation.isEmpty()) {
            //end condition for recursive algorithm. check for empty to avoid generating a schedule if the list of desired movies is empty at the start
            Schedule currentSchedule = new Schedule(currentPermutation, theatre, ignorePreviews);
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
                nextAvailableStartTime = showtime.getStartDateTime(ignorePreviews)
                                                 .plus(movie.getRunTime());
                generateSchedule(theatre, remainingMovies, nextAvailableStartTime, possibleSchedules, currentPermutation);
                currentPermutation.removeLast();
            }
        }
    }

    private boolean validateSchedule(Schedule schedule) {
        return (maxTotalDelay == null
                || schedule.getTotalDelay().isShorterThan(maxTotalDelay))
                && (maxIndividualDelay == null
                || schedule.getDelays().size() == 0
                || Collections.max(schedule.getDelays().values()).isShorterThan(maxIndividualDelay));
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
        return showtime.getStartDateTime(ignorePreviews).isAfter(startTime.minus(maxOverlap))
                && (latestTime == null
                || showtime.getEndDateTime().isBefore(latestTime));
    }

    public static class Builder {
        private static final int MAX_OVERLAP_MINUTES = 100;
        private ScheduleGenerator scheduleGenerator;

        public Builder(List<Movie> allMovies) {
            scheduleGenerator = new ScheduleGenerator();
            scheduleGenerator.earliestTime = new DateTime(0);
            scheduleGenerator.allMovies = allMovies;
            scheduleGenerator.allTheatres = reorganizeMoviesIntoModel(allMovies);
        }

        public Builder sortByDelay(boolean sortByDelay) {
            scheduleGenerator.sortByDelay = sortByDelay;
            return this;
        }

        public Builder ignorePreviews(boolean ignorePreviews) {
            scheduleGenerator.ignorePreviews = ignorePreviews;
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
