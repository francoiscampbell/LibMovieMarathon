
package io.github.francoiscampbell.apimodel;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Generated("org.jsonschema2pojo")
public class ApiTheatre {

    @Expose
    private String id;
    @Expose
    private String name;

    private List<ApiShowtime> showtimes = new ArrayList<>();

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

    public ApiTheatre withId(String id) {
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

    public ApiTheatre withName(String name) {
        this.name = name;
        return this;
    }

    public List<ApiShowtime> getShowtimes() {
        return showtimes;
    }

    public Set<ApiMovie> getMoviesPlayingHere() {
        Set<ApiMovie> movies = new HashSet<>();
        for (ApiShowtime s : showtimes) {
            movies.add(s.getMovie());
        }
        return movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiTheatre apiTheatre = (ApiTheatre) o;

        if (!id.equals(apiTheatre.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
