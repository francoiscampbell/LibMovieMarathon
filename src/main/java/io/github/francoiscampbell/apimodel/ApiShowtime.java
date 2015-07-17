
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ApiShowtime implements Comparable<ApiShowtime> {

    @Expose
    @SerializedName("theatre")
    private ApiTheatre apiTheatre;
    @Expose
    private DateTime dateTime;
    @Expose
    private String quals;
    @Expose
    private boolean barg;

    private ApiMovie movie;

    /**
     * 
     * @return
     *     The apiTheatre
     */
    public ApiTheatre getApiTheatre() {
        return apiTheatre;
    }

    /**
     * 
     * @param apiTheatre
     *     The apiTheatre
     */
    public void setApiTheatre(ApiTheatre apiTheatre) {
        this.apiTheatre = apiTheatre;
    }

    public ApiShowtime withTheatre(ApiTheatre apiTheatre) {
        this.apiTheatre = apiTheatre;
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

    public ApiShowtime withDateTime(DateTime dateTime) {
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

    public ApiShowtime withQuals(String quals) {
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

    public ApiShowtime withBarg(boolean barg) {
        this.barg = barg;
        return this;
    }

    public ApiMovie getMovie() {
        return movie;
    }

    public void setMovie(ApiMovie movie) {
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
    public int compareTo(@NotNull ApiShowtime o) {
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

        ApiShowtime apiShowtime = (ApiShowtime) o;

        if (!apiTheatre.equals(apiShowtime.apiTheatre)) return false;
        if (!dateTime.equals(apiShowtime.dateTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = apiTheatre.hashCode();
        result = 31 * result + dateTime.hashCode();
        return result;
    }
}
