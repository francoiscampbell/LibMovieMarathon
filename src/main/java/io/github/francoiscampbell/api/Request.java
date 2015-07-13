package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.ApiMovie;
import retrofit.RestAdapter;

import java.util.List;

/**
 * Created by francois on 15-07-10.
 */
public class Request {
    private RestAdapter endpoint;
    private String apiKey;

    private String postcode;
    private Float lat;
    private Float lon;
    private String startDate;
    private Integer numDays;
    private String radiusUnit;
    private Float searchRadius;

    private Request(Builder builder) {
        endpoint = builder.endpoint;
        apiKey = builder.apiKey;

        postcode = builder.postcode;
        lat = builder.lat;
        lon = builder.lon;
        startDate = builder.startDate;
        numDays = builder.numDays;
        radiusUnit = builder.radiusUnit;
        searchRadius = builder.searchRadius;
    }

    public List<ApiMovie> execute() {
        MovieApi api = endpoint.create(MovieApi.class);
        return api.getMovies(
                startDate, //date
                numDays, //num days (default 1 if null)
                postcode,
                lat, //latitude (not needed if using postcode)
                lon, //longitude (not needed if using postcode)
                searchRadius, //radius (default 5 if null)
                radiusUnit, //radius unit (default miles if null)
                apiKey);
    }

    public static class Builder {
        private RestAdapter endpoint;
        private String apiKey;

        private String postcode;
        private Float lat;
        private Float lon;
        private String startDate;
        private Integer numDays;
        private String radiusUnit;
        private Float searchRadius;


        public Builder(String startDate) {
            this.startDate = startDate;
        }

        public Builder(Builder builder) {
            postcode = builder.postcode;
            lat = builder.lat;
            lon = builder.lon;
            startDate = builder.startDate;
            numDays = builder.numDays;
            radiusUnit = builder.radiusUnit;
            searchRadius = builder.searchRadius;
        }

        public Builder endpoint(RestAdapter endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public Builder latlon(Float lat, Float lon) {
            this.lat = lat;
            this.lon = lon;
            return this;
        }

        public Builder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder numDays(Integer numDays) {
            this.numDays = numDays;
            return this;
        }

        public Builder radiusUnit(RadiusUnit radiusUnit) {
            this.radiusUnit = radiusUnit.getUnitString();
            return this;
        }

        public Builder searchRadius(Float searchRadius) {
            this.searchRadius = searchRadius;
            return this;
        }

        public Request build() {
            return new Request(this);
        }

    }

    /**
     * Created by francois on 15-07-10.
     */
    public enum RadiusUnit {
        KM("km"),
        MILES("mi");

        private String unitString;

        RadiusUnit(String unitString) {
            this.unitString = unitString;
        }

        public String getUnitString() {
            return unitString;
        }
    }
}
