package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.ApiMovie;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by francois on 15-07-03.
 */
public class Movie {
    private String id;
    private String name;
    private Duration runningLength;
    private List<Showtime> showtimes;

    public Movie(String runningLength, String name, String id) {
        this.runningLength = Duration.parse(runningLength);
        this.name = name;
        this.id = id;
        showtimes = new ArrayList<>();
    }

    public Movie(ApiMovie apiMovie){
        this(apiMovie.getRunTime(), apiMovie.getTitle(), apiMovie.getTmsId());
    }

    public void addShowtime(Showtime showtime){
        showtimes.add(showtime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return Objects.equals(id, movie.id);

    }
}
