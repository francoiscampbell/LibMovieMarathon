package io.github.francoiscampbell.api;

import io.github.francoiscampbell.apimodel.*;
import retrofit.http.*;
import rx.*;

/**
 * Created by francois on 15-07-25.
 */
public interface OmdbApi {
    @GET("/")
    Observable<OmdbMovie> getMovieInfo(@Query("t") String title);
}
