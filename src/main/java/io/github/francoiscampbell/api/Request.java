package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.ApiMovie;
import retrofit.RestAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by francois on 15-07-10.
 */
public class Request {
    private RestAdapter endpoint;
    private Map<String, String> queryParams;

    private Request(Builder builder) {
        endpoint = builder.endpoint;
        this.queryParams = builder.queryParams;
    }

    public List<ApiMovie> execute() {
        MovieApi api = endpoint.create(MovieApi.class);
        return api.getMovies(queryParams);
    }

    public static class Builder {
        private RestAdapter endpoint;
        private Map<String, String> queryParams;


        public Builder(String startDate) {
            queryParams = new HashMap<>();
            queryParams.put("startDate", startDate);
        }

        public Builder(Builder builder) {
            this.queryParams = builder.queryParams;
        }

        public Builder endpoint(RestAdapter endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder apiKey(String apiKey) {
            queryParams.put("api_key", apiKey);
            return this;
        }

        public Builder postcode(String postcode) {
            queryParams.put("zip", postcode);
            return this;
        }

        public Builder latlon(float lat, float lon) {
            queryParams.put("lat", String.valueOf(lat));
            queryParams.put("lon", String.valueOf(lon));
            return this;
        }

        public Builder startDate(String startDate) {
            queryParams.put("startDate", startDate);
            return this;
        }

        public Builder numDays(int numDays) {
            queryParams.put("numDays", String.valueOf(numDays));
            return this;
        }

        public Builder radiusUnit(RadiusUnit radiusUnit) {
            queryParams.put("units", radiusUnit.getUnitString());
            return this;
        }

        public Builder searchRadius(float searchRadius) {
            queryParams.put("radius", String.valueOf(searchRadius));
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
