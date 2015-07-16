package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.ApiMovie;
import retrofit.http.GET;
import retrofit.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * Created by francois on 15-07-02.
 */
public interface MovieApi {

    @GET("/v1.1/movies/showings")
    List<ApiMovie> getMovies(@QueryMap Map<String, String> queryParams);
}
