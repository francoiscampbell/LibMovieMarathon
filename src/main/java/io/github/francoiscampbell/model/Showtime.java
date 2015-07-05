package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.ApiShowtime;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDateTime;

/**
 * Created by francois on 15-07-03.
 */
public class Showtime implements Comparable<Showtime> {
    private final LocalDateTime dateTime;
    private final Movie movie;

    public Showtime(String dateTime, Movie movie) {
        this.dateTime = LocalDateTime.parse(dateTime);
        this.movie = movie;
    }

    public Showtime(ApiShowtime apiShowtime, Movie movie) {
        this(apiShowtime.getDateTime(), movie);
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getTime() {
        return String.format("%02d:%02d", dateTime.getHourOfDay(), dateTime.getMinuteOfHour());
    }

    @Override
    public int compareTo(@NotNull Showtime o) {
        return dateTime.compareTo(o.getDateTime());
    }

    @Override
    public String toString() {
        return dateTime.toString() + " " + movie.toString();
    }

}
