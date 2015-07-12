package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.ApiMovie;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

import java.util.List;

/**
 * Created by francois on 15-07-02.
 */
public interface MovieApi {

    @GET("/v1.1/movies/showings")
    void getMovies(
            @Query("startDate") String startDate,
            @Query("numDays") Integer numDays,
            @Query("zip") String postcode,
            @Query("lat") Float lat,
            @Query("lng") Float lng,
            @Query("radius") Float radius,
            @Query("units") String units,
            @Query("api_key") String apiKey,
            Callback<List<ApiMovie>> response
    );

    @GET("/v1.1/movies/showings")
    Observable<List<ApiMovie>> getMovies(
            @Query("startDate") String startDate,
            @Query("numDays") Integer numDays,
            @Query("zip") String postcode,
            @Query("lat") Float lat,
            @Query("lng") Float lng,
            @Query("radius") Float radius,
            @Query("units") String units,
            @Query("api_key") String apiKey
    );
}
