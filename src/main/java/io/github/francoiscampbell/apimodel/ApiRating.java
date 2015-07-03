
package io.github.francoiscampbell.apimodel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class ApiRating {

    @Expose
    private String body;
    @Expose
    private String code;

    /**
     * 
     * @return
     *     The body
     */
    public String getBody() {
        return body;
    }

    /**
     * 
     * @param body
     *     The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    public ApiRating withBody(String body) {
        this.body = body;
        return this;
    }

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    public ApiRating withCode(String code) {
        this.code = code;
        return this;
    }

}
