
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Rating {

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

    public Rating withBody(String body) {
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

    public Rating withCode(String code) {
        this.code = code;
        return this;
    }

}
