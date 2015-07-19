package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.Movie;
import retrofit.http.GET;
import retrofit.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * Created by francois on 15-07-02.
 */
public interface MovieApi {

    @GET("/v1.1/movies/showings")
    List<Movie> getMovies(@QueryMap Map<String, String> queryParams);
}
