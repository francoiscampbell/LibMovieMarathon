
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Showtime implements Comparable<Showtime> {

    @Expose
    private Theatre theatre;
    @Expose
    private DateTime dateTime;
    @Expose
    private String quals;
    @Expose
    private boolean barg;

    private Movie movie;

    /**
     * 
     * @return
     *     The theatre
     */
    public Theatre getTheatre() {
        return theatre;
    }

    /**
     *
     * @param theatre
     *     The theatre
     */
    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Showtime withTheatre(Theatre theatre) {
        this.theatre = theatre;
        return this;
    }

    /**
     * 
     * @return
     *     The dateTime
     */
    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * 
     * @param dateTime
     *     The dateTime
     */
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Showtime withDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    /**
     * 
     * @return
     *     The quals
     */
    public String getQuals() {
        return quals;
    }

    /**
     * 
     * @param quals
     *     The quals
     */
    public void setQuals(String quals) {
        this.quals = quals;
    }

    public Showtime withQuals(String quals) {
        this.quals = quals;
        return this;
    }

    /**
     * 
     * @return
     *     The barg
     */
    public boolean isBarg() {
        return barg;
    }

    /**
     * 
     * @param barg
     *     The barg
     */
    public void setBarg(boolean barg) {
        this.barg = barg;
    }

    public Showtime withBarg(boolean barg) {
        this.barg = barg;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Showtime showtime = (Showtime) o;

        if (!theatre.equals(showtime.theatre)) return false;
        if (!dateTime.equals(showtime.dateTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = movie.hashCode();
        result = 31 * result + dateTime.hashCode();
        return result;
    }
}
