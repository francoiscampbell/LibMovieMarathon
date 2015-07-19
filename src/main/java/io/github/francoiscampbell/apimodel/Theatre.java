
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Generated("org.jsonschema2pojo")
public class Theatre {

    @Expose
    private String id;
    @Expose
    private String name;

    private List<Showtime> showtimes = new ArrayList<>();

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Theatre withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Theatre withName(String name) {
        this.name = name;
        return this;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }

    public Set<Movie> getMoviesPlayingHere() {
        Set<Movie> movies = new HashSet<>();
        for (Showtime s : showtimes) {
            movies.add(s.getMovie());
        }
        return movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theatre theatre = (Theatre) o;

        if (!id.equals(theatre.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
