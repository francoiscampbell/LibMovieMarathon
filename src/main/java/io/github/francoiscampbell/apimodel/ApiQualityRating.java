
package io.github.francoiscampbell.apimodel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class ApiQualityRating {

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

    public ApiQualityRating withRatingsBody(String ratingsBody) {
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

    public ApiQualityRating withValue(String value) {
        this.value = value;
        return this;
    }

}
