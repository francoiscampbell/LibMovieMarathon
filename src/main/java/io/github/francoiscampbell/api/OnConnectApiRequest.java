package io.github.francoiscampbell.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.francoiscampbell.apimodel.Movie;
import io.github.francoiscampbell.gson.DateTimeConverter;
import io.github.francoiscampbell.gson.DurationConverter;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by francois on 15-07-10.
 */
public class OnConnectApiRequest {
    private static final String API_URL = "http://data.tmsapi.com";

    private MovieApi api;
    private Map<String, String> queryParams;

    private OnConnectApiRequest(Builder builder) {
        api = builder.endpointBuilder.build().create(MovieApi.class);

        this.queryParams = builder.queryParams;
    }

    public List<Movie> execute() {
        return api.getMovies(queryParams);
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

    public static class Builder {
        private RestAdapter.Builder endpointBuilder;
        private Map<String, String> queryParams;


        public Builder(String startDate) {
            queryParams = new HashMap<>();
            queryParams.put("startDate", startDate);

            Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeConverter())
                                         .registerTypeAdapter(Duration.class, new DurationConverter())
                                         .create();

            endpointBuilder = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setConverter(new GsonConverter(gson));
        }

        public Builder(Builder builder) {
            this.queryParams = builder.queryParams;
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

        public Builder logLevel(RestAdapter.LogLevel logLevel) {
            endpointBuilder.setLogLevel(logLevel);
            return this;
        }

        public OnConnectApiRequest build() {
            return new OnConnectApiRequest(this);
        }

    }
}
