
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PreferredImage {

    @Expose
    private String width;
    @Expose
    private String height;
    @Expose
    private Caption caption;
    @Expose
    private String uri;
    @Expose
    private String category;
    @Expose
    private String text;
    @Expose
    private String primary;

    /**
     * 
     * @return
     *     The width
     */
    public String getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    public PreferredImage withWidth(String width) {
        this.width = width;
        return this;
    }

    /**
     * 
     * @return
     *     The height
     */
    public String getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    public PreferredImage withHeight(String height) {
        this.height = height;
        return this;
    }

    /**
     * 
     * @return
     *     The caption
     */
    public Caption getCaption() {
        return caption;
    }

    /**
     *
     * @param caption
     *     The caption
     */
    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    public PreferredImage withCaption(Caption caption) {
        this.caption = caption;
        return this;
    }

    /**
     * 
     * @return
     *     The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * 
     * @param uri
     *     The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    public PreferredImage withUri(String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * 
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public PreferredImage withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    public PreferredImage withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * 
     * @return
     *     The primary
     */
    public String getPrimary() {
        return primary;
    }

    /**
     * 
     * @param primary
     *     The primary
     */
    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public PreferredImage withPrimary(String primary) {
        this.primary = primary;
        return this;
    }

}
