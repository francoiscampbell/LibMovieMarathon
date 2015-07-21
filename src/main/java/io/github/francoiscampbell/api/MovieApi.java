package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.*;
import retrofit.*;
import retrofit.http.*;

import java.util.*;

/**
 * Created by francois on 15-07-02.
 */
public interface MovieApi {

    @GET("/v1.1/movies/showings")
    List<Movie> getMovies(@QueryMap Map<String, String> queryParams);

    @GET("/v1.1/movies/showings")
    void getMovies(@QueryMap Map<String, String> queryParams, Callback<List<Movie>> callback);
}
