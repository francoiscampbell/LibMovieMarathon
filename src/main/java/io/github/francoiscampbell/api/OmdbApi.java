package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.OmdbMovie;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by francois on 15-07-25.
 */
public interface OmdbApi {
    @GET("/")
    Observable<OmdbMovie> getMovieInfo(@Query("t") String title);
}
