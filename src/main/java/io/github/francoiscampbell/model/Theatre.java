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
    private List<Movie> movieList;

    private int previewsLength;

    public Theatre(String id, String name) {
        this.id = id;
        this.name = name;
        movieList = new ArrayList<>();
        previewsLength = 15; //default
    }

    public Theatre(ApiTheatre apiTheatre){
        this(apiTheatre.getId(),apiTheatre.getName());
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theatre theatre = (Theatre) o;

        return Objects.equals(id, theatre.id);

    }
}
