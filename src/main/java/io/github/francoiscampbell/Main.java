package io.github.francoiscampbell;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.francoiscampbell.api.MovieApi;
import io.github.francoiscampbell.apimodel.ApiMovie;
import io.github.francoiscampbell.apimodel.ApiShowtime;
import io.github.francoiscampbell.model.Movie;
import io.github.francoiscampbell.model.Showtime;
import io.github.francoiscampbell.model.Theatre;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francois on 15-07-02.
 */
public class Main {
    public static final String API_URL = "http://data.tmsapi.com";
    private List<Theatre> theatres;

    public Main() {
        theatres = new ArrayList<>();
    }

    public void getMovies(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        MovieApi api = restAdapter.create(MovieApi.class);

        String currentDate = "2015-07-03";
        String postcode = "M5T 1N5";
        String apiKey = "xv4za7trkge9yrz4b4h6ws9s";

        api.getMovies(currentDate, postcode, apiKey, new Callback<List<ApiMovie>>() {
            @Override
            public void success(List<ApiMovie> apiMovieList, Response response) {
                reorganizeMovies(apiMovieList);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error = " + error);
            }
        });

    }

    private void reorganizeMovies(List<ApiMovie> apiMovieList) {
        for (ApiMovie apiMovie : apiMovieList){
            if ("Theatre Event".equals(apiMovie.getSubType())){
                break;
            }
            for (ApiShowtime apiShowtime : apiMovie.getApiShowtimes()){
                Theatre theatre = new Theatre(apiShowtime.getApiTheatre());
                try {
                    theatre = theatres.get(theatres.indexOf(theatre));
                } catch (IndexOutOfBoundsException e){
                    theatres.add(theatre);
                }

                Movie movie = new Movie(apiMovie);
                List<Movie> moviesAtThisTheatre = theatre.getMovieList();
                try {
                    movie = moviesAtThisTheatre.get(moviesAtThisTheatre.indexOf(movie));
                } catch (IndexOutOfBoundsException e){
                    moviesAtThisTheatre.add(movie);
                }
                movie.addShowtime(new Showtime(apiShowtime));
            }
        }
        System.out.println("done");
    }

    public static void main(String[] args) {
        new Main().getMovies();
    }
}
