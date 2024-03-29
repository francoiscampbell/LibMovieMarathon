
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Generated("org.jsonschema2pojo")
public class Movie {

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
    private DateTime releaseDate;
    @Expose
    private String titleLang;
    @Expose
    private String descriptionLang;
    @Expose
    private String entityType;
    @Expose
    private List<String> genres = new ArrayList<>();
    @Expose
    private String longDescription;
    @Expose
    private String shortDescription;
    @Expose
    private List<String> topCast = new ArrayList<>();
    @Expose
    private List<String> directors = new ArrayList<>();
    @Expose
    private String officialUrl;
    @Expose
    private QualityRating qualityRating;
    @Expose
    private List<Rating> ratings = new ArrayList<>();
    @Expose
    private List<String> advisories = new ArrayList<>();
    @Expose
    private Duration runTime;
    @Expose
    private PreferredImage preferredImage;
    @Expose
    private List<Showtime> showtimes = new ArrayList<>();

    /**
     * @return The tmsId
     */
    public String getTmsId() {
        return tmsId;
    }

    /**
     * @param tmsId The tmsId
     */
    public void setTmsId(String tmsId) {
        this.tmsId = tmsId;
    }

    public Movie withTmsId(String tmsId) {
        this.tmsId = tmsId;
        return this;
    }

    /**
     * @return The rootId
     */
    public String getRootId() {
        return rootId;
    }

    /**
     * @param rootId The rootId
     */
    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public Movie withRootId(String rootId) {
        this.rootId = rootId;
        return this;
    }

    /**
     * @return The subType
     */
    public String getSubType() {
        return subType;
    }

    /**
     * @param subType The subType
     */
    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Movie withSubType(String subType) {
        this.subType = subType;
        return this;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Movie withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @return The releaseYear
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * @param releaseYear The releaseYear
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Movie withReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    /**
     * @return The releaseDate
     */
    public DateTime getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate The releaseDate
     */
    public void setReleaseDate(DateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Movie withReleaseDate(DateTime releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    /**
     * @return The titleLang
     */
    public String getTitleLang() {
        return titleLang;
    }

    /**
     * @param titleLang The titleLang
     */
    public void setTitleLang(String titleLang) {
        this.titleLang = titleLang;
    }

    public Movie withTitleLang(String titleLang) {
        this.titleLang = titleLang;
        return this;
    }

    /**
     * @return The descriptionLang
     */
    public String getDescriptionLang() {
        return descriptionLang;
    }

    /**
     * @param descriptionLang The descriptionLang
     */
    public void setDescriptionLang(String descriptionLang) {
        this.descriptionLang = descriptionLang;
    }

    public Movie withDescriptionLang(String descriptionLang) {
        this.descriptionLang = descriptionLang;
        return this;
    }

    /**
     * @return The entityType
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * @param entityType The entityType
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Movie withEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    /**
     * @return The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * @param genres The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Movie withGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    /**
     * @return The longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * @param longDescription The longDescription
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Movie withLongDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    /**
     * @return The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @param shortDescription The shortDescription
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Movie withShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    /**
     * @return The topCast
     */
    public List<String> getTopCast() {
        return topCast;
    }

    /**
     * @param topCast The topCast
     */
    public void setTopCast(List<String> topCast) {
        this.topCast = topCast;
    }

    public Movie withTopCast(List<String> topCast) {
        this.topCast = topCast;
        return this;
    }

    /**
     * @return The directors
     */
    public List<String> getDirectors() {
        return directors;
    }

    /**
     * @param directors The directors
     */
    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public Movie withDirectors(List<String> directors) {
        this.directors = directors;
        return this;
    }

    /**
     * @return The officialUrl
     */
    public String getOfficialUrl() {
        return officialUrl;
    }

    /**
     * @param officialUrl The officialUrl
     */
    public void setOfficialUrl(String officialUrl) {
        this.officialUrl = officialUrl;
    }

    public Movie withOfficialUrl(String officialUrl) {
        this.officialUrl = officialUrl;
        return this;
    }

    /**
     * @return The qualityRating
     */
    public QualityRating getQualityRating() {
        return qualityRating;
    }

    /**
     * @param qualityRating The qualityRating
     */
    public void setQualityRating(QualityRating qualityRating) {
        this.qualityRating = qualityRating;
    }

    public Movie withQualityRating(QualityRating qualityRating) {
        this.qualityRating = qualityRating;
        return this;
    }

    /**
     * @return The ratings
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * @param ratings The ratings
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Movie withRatings(List<Rating> ratings) {
        this.ratings = ratings;
        return this;
    }

    /**
     * @return The advisories
     */
    public List<String> getAdvisories() {
        return advisories;
    }

    /**
     * @param advisories The advisories
     */
    public void setAdvisories(List<String> advisories) {
        this.advisories = advisories;
    }

    public Movie withAdvisories(List<String> advisories) {
        this.advisories = advisories;
        return this;
    }

    /**
     * @return The runTime
     */
    public Duration getRunTime() {
        return runTime;
    }

    /**
     * @param runTime The runTime
     */
    public void setRunTime(Duration runTime) {
        this.runTime = runTime;
    }

    public Movie withRunTime(Duration runTime) {
        this.runTime = runTime;
        return this;
    }

    /**
     * @return The preferredImage
     */
    public PreferredImage getPreferredImage() {
        return preferredImage;
    }

    /**
     * @param preferredImage The preferredImage
     */
    public void setPreferredImage(PreferredImage preferredImage) {
        this.preferredImage = preferredImage;
    }

    public Movie withPreferredImage(PreferredImage preferredImage) {
        this.preferredImage = preferredImage;
        return this;
    }

    /**
     * @return The showtimes
     */
    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    /**
     * @param showtimes The showtimes
     */
    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    public Movie withShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
        return this;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return Objects.equals(tmsId, movie.tmsId);
    }

    @Override
    public int hashCode() {
        return tmsId.hashCode();
    }
}
