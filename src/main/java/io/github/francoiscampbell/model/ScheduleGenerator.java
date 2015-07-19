package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.Movie;
import io.github.francoiscampbell.apimodel.Showtime;
import io.github.francoiscampbell.apimodel.Theatre;
import io.github.francoiscampbell.collections.SelfMap;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created by francois on 15-07-19.
 */
public class ScheduleGenerator {
    private List<Theatre> allTheatres;
    private List<Theatre> possibleTheatres;

    private List<Movie> desiredMovies;
    private boolean sortByDelay;

    public List<Schedule> generateSchedules() {
        possibleTheatres = calculatePossibleTheatres(allTheatres, desiredMovies);
        List<Schedule> possibleSchedules = new ArrayList<>();
        Deque<Showtime> currentPermutation = new LinkedList<>();
        for (Theatre t : possibleTheatres) {
            generateSchedule(t, desiredMovies, new DateTime(0), possibleSchedules, currentPermutation);
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

    public static class Builder {
        private ScheduleGenerator scheduleGenerator;

        public Builder(List<Movie> allMovies) {
            scheduleGenerator = new ScheduleGenerator();
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
