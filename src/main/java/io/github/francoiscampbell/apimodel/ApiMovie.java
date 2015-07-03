
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class ApiMovie {

    @Expose
    private String tmsId;
    @Expose
    private String rootId;
    @Expose
    private String subType;
    @Expose
    private String title;
    @Expose
    private int releaseYear;
    @Expose
    private String releaseDate;
    @Expose
    private String titleLang;
    @Expose
    private String descriptionLang;
    @Expose
    private String entityType;
    @Expose
    private List<String> genres = new ArrayList<String>();
    @Expose
    private String longDescription;
    @Expose
    private String shortDescription;
    @Expose
    private List<String> topCast = new ArrayList<String>();
    @Expose
    private List<String> directors = new ArrayList<String>();
    @Expose
    private String officialUrl;
    @Expose
    @SerializedName("qualityRating")
    private ApiQualityRating apiQualityRating;
    @Expose
    @SerializedName("ratings")
    private List<ApiRating> apiRatings = new ArrayList<ApiRating>();
    @Expose
    private List<String> advisories = new ArrayList<String>();
    @Expose
    private String runTime;
    @Expose
    @SerializedName("preferredImage")
    private ApiPreferredImage apiPreferredImage;
    @Expose
    @SerializedName("showtimes")
    private List<ApiShowtime> apiShowtimes = new ArrayList<ApiShowtime>();

    /**
     * 
     * @return
     *     The tmsId
     */
    public String getTmsId() {
        return tmsId;
    }

    /**
     * 
     * @param tmsId
     *     The tmsId
     */
    public void setTmsId(String tmsId) {
        this.tmsId = tmsId;
    }

    public ApiMovie withTmsId(String tmsId) {
        this.tmsId = tmsId;
        return this;
    }

    /**
     * 
     * @return
     *     The rootId
     */
    public String getRootId() {
        return rootId;
    }

    /**
     * 
     * @param rootId
     *     The rootId
     */
    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public ApiMovie withRootId(String rootId) {
        this.rootId = rootId;
        return this;
    }

    /**
     * 
     * @return
     *     The subType
     */
    public String getSubType() {
        return subType;
    }

    /**
     * 
     * @param subType
     *     The subType
     */
    public void setSubType(String subType) {
        this.subType = subType;
    }

    public ApiMovie withSubType(String subType) {
        this.subType = subType;
        return this;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public ApiMovie withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * @return
     *     The releaseYear
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * 
     * @param releaseYear
     *     The releaseYear
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ApiMovie withReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    /**
     * 
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * 
     * @param releaseDate
     *     The releaseDate
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ApiMovie withReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    /**
     * 
     * @return
     *     The titleLang
     */
    public String getTitleLang() {
        return titleLang;
    }

    /**
     * 
     * @param titleLang
     *     The titleLang
     */
    public void setTitleLang(String titleLang) {
        this.titleLang = titleLang;
    }

    public ApiMovie withTitleLang(String titleLang) {
        this.titleLang = titleLang;
        return this;
    }

    /**
     * 
     * @return
     *     The descriptionLang
     */
    public String getDescriptionLang() {
        return descriptionLang;
    }

    /**
     * 
     * @param descriptionLang
     *     The descriptionLang
     */
    public void setDescriptionLang(String descriptionLang) {
        this.descriptionLang = descriptionLang;
    }

    public ApiMovie withDescriptionLang(String descriptionLang) {
        this.descriptionLang = descriptionLang;
        return this;
    }

    /**
     * 
     * @return
     *     The entityType
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * 
     * @param entityType
     *     The entityType
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public ApiMovie withEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    /**
     * 
     * @return
     *     The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * 
     * @param genres
     *     The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public ApiMovie withGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    /**
     * 
     * @return
     *     The longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * 
     * @param longDescription
     *     The longDescription
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public ApiMovie withLongDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    /**
     * 
     * @return
     *     The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * 
     * @param shortDescription
     *     The shortDescription
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public ApiMovie withShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    /**
     * 
     * @return
     *     The topCast
     */
    public List<String> getTopCast() {
        return topCast;
    }

    /**
     * 
     * @param topCast
     *     The topCast
     */
    public void setTopCast(List<String> topCast) {
        this.topCast = topCast;
    }

    public ApiMovie withTopCast(List<String> topCast) {
        this.topCast = topCast;
        return this;
    }

    /**
     * 
     * @return
     *     The directors
     */
    public List<String> getDirectors() {
        return directors;
    }

    /**
     * 
     * @param directors
     *     The directors
     */
    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public ApiMovie withDirectors(List<String> directors) {
        this.directors = directors;
        return this;
    }

    /**
     * 
     * @return
     *     The officialUrl
     */
    public String getOfficialUrl() {
        return officialUrl;
    }

    /**
     * 
     * @param officialUrl
     *     The officialUrl
     */
    public void setOfficialUrl(String officialUrl) {
        this.officialUrl = officialUrl;
    }

    public ApiMovie withOfficialUrl(String officialUrl) {
        this.officialUrl = officialUrl;
        return this;
    }

    /**
     * 
     * @return
     *     The apiQualityRating
     */
    public ApiQualityRating getApiQualityRating() {
        return apiQualityRating;
    }

    /**
     * 
     * @param apiQualityRating
     *     The apiQualityRating
     */
    public void setApiQualityRating(ApiQualityRating apiQualityRating) {
        this.apiQualityRating = apiQualityRating;
    }

    public ApiMovie withQualityRating(ApiQualityRating apiQualityRating) {
        this.apiQualityRating = apiQualityRating;
        return this;
    }

    /**
     * 
     * @return
     *     The apiRatings
     */
    public List<ApiRating> getApiRatings() {
        return apiRatings;
    }

    /**
     * 
     * @param apiRatings
     *     The apiRatings
     */
    public void setApiRatings(List<ApiRating> apiRatings) {
        this.apiRatings = apiRatings;
    }

    public ApiMovie withRatings(List<ApiRating> apiRatings) {
        this.apiRatings = apiRatings;
        return this;
    }

    /**
     * 
     * @return
     *     The advisories
     */
    public List<String> getAdvisories() {
        return advisories;
    }

    /**
     * 
     * @param advisories
     *     The advisories
     */
    public void setAdvisories(List<String> advisories) {
        this.advisories = advisories;
    }

    public ApiMovie withAdvisories(List<String> advisories) {
        this.advisories = advisories;
        return this;
    }

    /**
     * 
     * @return
     *     The runTime
     */
    public String getRunTime() {
        return runTime;
    }

    /**
     * 
     * @param runTime
     *     The runTime
     */
    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public ApiMovie withRunTime(String runTime) {
        this.runTime = runTime;
        return this;
    }

    /**
     * 
     * @return
     *     The apiPreferredImage
     */
    public ApiPreferredImage getApiPreferredImage() {
        return apiPreferredImage;
    }

    /**
     * 
     * @param apiPreferredImage
     *     The apiPreferredImage
     */
    public void setApiPreferredImage(ApiPreferredImage apiPreferredImage) {
        this.apiPreferredImage = apiPreferredImage;
    }

    public ApiMovie withPreferredImage(ApiPreferredImage apiPreferredImage) {
        this.apiPreferredImage = apiPreferredImage;
        return this;
    }

    /**
     * 
     * @return
     *     The apiShowtimes
     */
    public List<ApiShowtime> getApiShowtimes() {
        return apiShowtimes;
    }

    /**
     * 
     * @param apiShowtimes
     *     The apiShowtimes
     */
    public void setApiShowtimes(List<ApiShowtime> apiShowtimes) {
        this.apiShowtimes = apiShowtimes;
    }

    public ApiMovie withShowtimes(List<ApiShowtime> apiShowtimes) {
        this.apiShowtimes = apiShowtimes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiMovie apiMovie = (ApiMovie) o;

        if (!tmsId.equals(apiMovie.tmsId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tmsId.hashCode();
    }
}
