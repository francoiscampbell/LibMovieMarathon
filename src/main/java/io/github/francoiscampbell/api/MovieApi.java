package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.ApiMovie;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

/**
 * Created by francois on 15-07-02.
 */
public interface MovieApi {

    @GET("/v1.1/movies/showings")
    void getMovies(@Query("startDate") String startDate, @Query("zip") String postcode, @Query("api_key") String apiKey, Callback<List<ApiMovie>> response);
}
