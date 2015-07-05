package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.ApiShowtime;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

/**
 * Created by francois on 15-07-03.
 */
public class Showtime implements Comparable<Showtime> {
    private final DateTime dateTime;
    private final Movie movie;

    public Showtime(String dateTime, Movie movie) {
        this.dateTime = DateTime.parse(dateTime);
        this.movie = movie;
    }

    public Showtime(ApiShowtime apiShowtime, Movie movie) {
        this(apiShowtime.getDateTime(), movie);
    }

    public Movie getMovie() {
        return movie;
    }

    public DateTime getStartDateTime() {
        return dateTime;
    }

    public DateTime getEndDateTime() {
        return dateTime.plus(getMovie().getTotalLength());
    }

    public String getStartTimeString() {
        return String.format("%02d:%02d", dateTime.getHourOfDay(), dateTime.getMinuteOfHour());
    }

    public String getEndTimeString() {
        DateTime endTime = getEndDateTime();
        return String.format("%02d:%02d", endTime.getHourOfDay(), endTime.getMinuteOfHour());
    }

    @Override
    public int compareTo(@NotNull Showtime o) {
        return dateTime.compareTo(o.getStartDateTime());
    }

    @Override
    public String toString() {
        return dateTime.toString() + "-" + getEndDateTime().toString() + " " + getMovie().toString();
    }

    public String toFriendlyString() {
        return getStartTimeString() + "-" + getEndTimeString() + " " + getMovie().toString();
    }

}
