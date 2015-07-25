package io.github.francoiscampbell.api;

import com.google.gson.*;
import io.github.francoiscampbell.apimodel.*;
import io.github.francoiscampbell.gson.*;
import org.joda.time.*;
import retrofit.*;
import retrofit.client.*;
import retrofit.converter.*;
import retrofit.mime.*;
import rx.Observable;

import java.util.*;

/**
 * Created by francois on 15-07-10.
 */
public class OnConnectApiRequest {

    private MovieApi movieApi;
    private OmdbApi omdbApi;
    private Map<String, String> queryParams;

    public List<Movie> execute() {
        List<Movie> movies = new LinkedList<>();
        final Movie[] currentMovie = new Movie[1];
        movieApi.getMovies(queryParams).flatMap(Observable::from)
                .doOnNext(m -> currentMovie[0] = m)
                .doOnNext(movies::add)
                .flatMap(m -> omdbApi.getMovieInfo(m.getTitle()))
                .doOnNext(m -> currentMovie[0].getPreferredImage().setUri(m.getPoster()))
                .toBlocking()
                .forEach(m -> System.out.println(currentMovie[0].getPreferredImage().getUri()));
        return movies;
    }

    public void execute(Callback<List<Movie>> callback) {
        movieApi.getMovies(queryParams, callback);
    }

    public static class Builder {
        private static final String ONCONNECT_API_URL = "http://data.tmsapi.com";
        private static final String OMDB_API_URL = "http://www.omdbapi.com";

        private OnConnectApiRequest request;
        private RestAdapter.Builder onConnectApiBuilder;
        private RestAdapter.Builder omdbApiBuilder;


        public Builder(String startDate) {
            request = new OnConnectApiRequest();

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
            onConnectApiBuilder.setLogLevel(logLevel);
            return this;
        }

        public Builder mockResponse(String mockResponse) {
            Client mockClient = request -> new Response(request
                    .getUrl(), 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json", mockResponse
                    .getBytes()));
            onConnectApiBuilder.setClient(mockClient);
            return this;
        }

        public OnConnectApiRequest build() {
            request.movieApi = onConnectApiBuilder.build().create(MovieApi.class);
            request.omdbApi = omdbApiBuilder.build().create(OmdbApi.class);
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
