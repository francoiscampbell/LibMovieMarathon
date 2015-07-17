package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.ApiShowtime;
import io.github.francoiscampbell.apimodel.ApiTheatre;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.*;

/**
 * Created by francois on 15-07-04.
 */
public class Schedule {
    private ApiTheatre theatre;
    private Deque<ApiShowtime> showtimes;
    private Map<ApiShowtime, Duration> delays;

    public Schedule(Deque<ApiShowtime> showtimes, ApiTheatre theatre) {
        this.showtimes = new LinkedList<>(showtimes);
        this.theatre = theatre;
        this.delays = calculateDelays(showtimes);
    }

    private Map<ApiShowtime, Duration> calculateDelays(Deque<ApiShowtime> showtimes) {
        Map<ApiShowtime, Duration> delays = new LinkedHashMap<>();
        Iterator<ApiShowtime> iterator = showtimes.iterator();
        ApiShowtime next = iterator.next();
        while (iterator.hasNext()) {
            ApiShowtime current = next;
            next = iterator.next();
            DateTime endTime = current.getEndDateTime();
            DateTime nextStartTime = next.getStartDateTime();
            delays.put(current, new Duration(endTime, nextStartTime));
        }
        return delays;
    }

    public Map<ApiShowtime, Duration> getDelays() {
        return delays;
    }

    public Duration getDelayAfterShowtime(ApiShowtime showtime) {
        return delays.get(showtime);
    }

    public Deque<ApiShowtime> getShowtimes() {
        return showtimes;
    }

    public ApiTheatre getTheatre() {
        return theatre;
    }
}
