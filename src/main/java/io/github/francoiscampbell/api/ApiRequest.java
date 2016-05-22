package io.github.francoiscampbell.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.francoiscampbell.apimodel.Movie;
import io.github.francoiscampbell.gson.DateTimeConverter;
import io.github.francoiscampbell.gson.DurationConverter;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import rx.Observable;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by francois on 15-07-10.
 */
public class ApiRequest {

    private MovieApi movieApi;
    private OmdbApi omdbApi;
    private Map<String, String> queryParams;

    public Observable<Movie> execute() {
        return movieApi.getMovies(queryParams)
                       .flatMap(Observable::from)
                       .flatMap(m -> omdbApi.getMovieInfo(m.getTitle())
                                            .map(omdbMovie -> {
                                                m.getPreferredImage().setUri(omdbMovie.getPoster());
                                                return m;
                                            })
                       );
    }

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
        private static final String ONCONNECT_API_URL = "http://data.tmsapi.com";
        private static final String OMDB_API_URL = "http://www.omdbapi.com";

        private ApiRequest request;
        private RestAdapter.Builder onConnectApiBuilder;
        private RestAdapter.Builder omdbApiBuilder;

        private Builder(String startDate) {
            request = new ApiRequest();

            request.queryParams = new HashMap<>();
            request.queryParams.put("startDate", startDate);

            Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeConverter())
                                         .registerTypeAdapter(Duration.class, new DurationConverter())
                                         .create();

            onConnectApiBuilder = new RestAdapter.Builder()
                    .setEndpoint(ONCONNECT_API_URL)
                    .setConverter(new GsonConverter(gson));

            omdbApiBuilder = new RestAdapter.Builder()
//                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(OMDB_API_URL);
        }

        public static Builder withPostcode(String startDate, String postcode) {
            return new Builder(startDate).postcode(postcode);
        }

        public static Builder withLatLng(String startDate, float lat, float lng) {
            return new Builder(startDate).latlng(lat, lng);
        }

        public Builder apiKey(String apiKey) {
            request.queryParams.put("api_key", apiKey);
            return this;
        }

        public Builder postcode(String postcode) {
            request.queryParams.put("zip", postcode);
            return this;
        }

        public Builder latlng(float lat, float lng) {
            request.queryParams.put("lat", String.valueOf(lat));
            request.queryParams.put("lng", String.valueOf(lng));
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
            onConnectApiBuilder.setLogLevel(logLevel);
            return this;
        }

        public Builder mockResponse(String mockResponse) {
            Client mockClient = request -> new Response(request
                    .getUrl(), 200, "nothing", Collections
                    .emptyList(),
                    new TypedByteArray("application/json", mockResponse.getBytes()));
            onConnectApiBuilder.setClient(mockClient);
            return this;
        }

        public Builder mockResponse(File mockResponseJsonFile) {
            Client mockClient = request -> new Response(request.getUrl(),
                    200,
                    "nothing",
                    Collections.emptyList(),
                    new TypedFile("application/json", mockResponseJsonFile));
            onConnectApiBuilder.setClient(mockClient);
            return this;
        }

        public ApiRequest build() {
            request.movieApi = onConnectApiBuilder.build().create(MovieApi.class);
            request.omdbApi = omdbApiBuilder.build().create(OmdbApi.class);
            return this.request;
        }
    }
}
