package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.ApiTheatre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by francois on 15-07-03.
 */
public class Theatre {
    private String id;
    private String name;
    private List<Showtime> showtimes;

    public Theatre(String id, String name) {
        this.id = id;
        this.name = name;
        showtimes = new ArrayList<>();
    }

    public Theatre(ApiTheatre apiTheatre){
        this(apiTheatre.getId(),apiTheatre.getName());
    }

    public String getName() {
        return name;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public List<Movie> getMoviesPlayingHere() {
        List<Movie> movies = new ArrayList<>();
        for (Showtime s : showtimes) {
            Movie m = s.getMovie();
            if (!movies.contains(m)) {
                movies.add(m);
            }
        }
        return movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theatre theatre = (Theatre) o;

        return Objects.equals(id, theatre.id);

    }

    @Override
    public String toString() {
        return name;
    }
}
