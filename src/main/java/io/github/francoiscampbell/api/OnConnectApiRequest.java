package io.github.francoiscampbell.api;

import com.google.gson.*;
import io.github.francoiscampbell.apimodel.*;
import io.github.francoiscampbell.gson.*;
import org.joda.time.*;
import retrofit.*;
import retrofit.client.*;
import retrofit.converter.*;
import retrofit.mime.*;

import java.util.*;

/**
 * Created by francois on 15-07-10.
 */
public class OnConnectApiRequest {

    private MovieApi api;
    private Map<String, String> queryParams;

    public List<Movie> execute() {
        return api.getMovies(queryParams);
    }

    public void execute(Callback<List<Movie>> callback) {
        api.getMovies(queryParams, callback);
    }

    public static class Builder {
        private static final String API_URL = "http://data.tmsapi.com";

        private OnConnectApiRequest request;
        private RestAdapter.Builder endpointBuilder;


        public Builder(String startDate) {
            request = new OnConnectApiRequest();

            request.queryParams = new HashMap<>();
            request.queryParams.put("startDate", startDate);

            Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeConverter())
                                         .registerTypeAdapter(Duration.class, new DurationConverter())
                                         .create();

            endpointBuilder = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setConverter(new GsonConverter(gson));
        }

        public Builder apiKey(String apiKey) {
            request.queryParams.put("api_key", apiKey);
            return this;
        }

        public Builder postcode(String postcode) {
            request.queryParams.put("zip", postcode);
            return this;
        }

        public Builder latlon(float lat, float lon) {
            request.queryParams.put("lat", String.valueOf(lat));
            request.queryParams.put("lon", String.valueOf(lon));
            return this;
        }

        public Builder startDate(String startDate) {
            request.queryParams.put("startDate", startDate);
            return this;
        }

        public Builder numDays(int numDays) {
            request.queryParams.put("numDays", String.valueOf(numDays));
            return this;
        }

        public Builder radiusUnit(RadiusUnit radiusUnit) {
            request.queryParams.put("units", radiusUnit.getUnitString());
            return this;
        }

        public Builder searchRadius(float searchRadius) {
            request.queryParams.put("radius", String.valueOf(searchRadius));
            return this;
        }

        public Builder logLevel(RestAdapter.LogLevel logLevel) {
            endpointBuilder.setLogLevel(logLevel);
            return this;
        }

        public Builder mockResponse(String mockResponse) {
            Client mockClient = request -> new Response(request
                    .getUrl(), 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json", mockResponse
                    .getBytes()));
            endpointBuilder.setClient(mockClient);
            return this;
        }

        public OnConnectApiRequest build() {
            request.api = endpointBuilder.build().create(MovieApi.class);
            return request;
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
}
