
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ApiCaption {

    @Expose
    private String content;
    @Expose
    private String lang;

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public ApiCaption withContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 
     * @return
     *     The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * 
     * @param lang
     *     The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    public ApiCaption withLang(String lang) {
        this.lang = lang;
        return this;
    }

}
