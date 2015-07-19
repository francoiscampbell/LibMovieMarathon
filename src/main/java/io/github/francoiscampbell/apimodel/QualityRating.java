
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class QualityRating {

    @Expose
    private String ratingsBody;
    @Expose
    private String value;

    /**
     * 
     * @return
     *     The ratingsBody
     */
    public String getRatingsBody() {
        return ratingsBody;
    }

    /**
     * 
     * @param ratingsBody
     *     The ratingsBody
     */
    public void setRatingsBody(String ratingsBody) {
        this.ratingsBody = ratingsBody;
    }

    public QualityRating withRatingsBody(String ratingsBody) {
        this.ratingsBody = ratingsBody;
        return this;
    }

    /**
     * 
     * @return
     *     The value
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    public QualityRating withValue(String value) {
        this.value = value;
        return this;
    }

}
