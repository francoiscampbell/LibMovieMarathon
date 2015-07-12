package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.ApiMovie;
import retrofit.Callback;
import retrofit.RestAdapter;
import rx.Observable;

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

    private Request(RequestBuilder builder) {
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

    public void execute(Callback<List<ApiMovie>> callback) {
        MovieApi api = endpoint.create(MovieApi.class);
        api.getMovies(
                startDate, //date
                numDays, //num days (default 1 if null)
                postcode,
                lat, //latitude (not needed if using postcode)
                lon, //longitude (not needed if using postcode)
                searchRadius, //radius (default 5 if null)
                radiusUnit, //radius unit (default miles if null)
                apiKey,
                callback);
    }

    public Observable<List<ApiMovie>> execute() {
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

    public static class RequestBuilder {
        private RestAdapter endpoint;
        private String apiKey;

        private String postcode;
        private Float lat;
        private Float lon;
        private String startDate;
        private Integer numDays;
        private String radiusUnit;
        private Float searchRadius;


        public RequestBuilder(String startDate) {
            this.startDate = startDate;
        }

        public RequestBuilder(RequestBuilder builder) {
            postcode = builder.postcode;
            lat = builder.lat;
            lon = builder.lon;
            startDate = builder.startDate;
            numDays = builder.numDays;
            radiusUnit = builder.radiusUnit;
            searchRadius = builder.searchRadius;
        }

        public RequestBuilder endpoint(RestAdapter endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public RequestBuilder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public RequestBuilder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public RequestBuilder latlon(Float lat, Float lon) {
            this.lat = lat;
            this.lon = lon;
            return this;
        }

        public RequestBuilder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public RequestBuilder numDays(Integer numDays) {
            this.numDays = numDays;
            return this;
        }

        public RequestBuilder radiusUnit(RadiusUnit radiusUnit) {
            this.radiusUnit = radiusUnit.getUnitString();
            return this;
        }

        public RequestBuilder searchRadius(Float searchRadius) {
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
