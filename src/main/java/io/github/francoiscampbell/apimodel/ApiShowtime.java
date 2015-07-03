
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.Date;

@Generated("org.jsonschema2pojo")
public class ApiShowtime {

    @Expose
    @SerializedName("theatre")
    private ApiTheatre apiTheatre;
    @Expose
    private Date dateTime;
    @Expose
    private String quals;
    @Expose
    private boolean barg;

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
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * 
     * @param dateTime
     *     The dateTime
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public ApiShowtime withDateTime(Date dateTime) {
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
