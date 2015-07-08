package io.github.francoiscampbell.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.*;

/**
 * Created by francois on 15-07-04.
 */
public class Schedule {
    private Theatre theatre;
    private Deque<Showtime> showtimes;
    private Map<Showtime, Duration> delays;

    public Schedule(Deque<Showtime> showtimes, Theatre theatre) {
        this.showtimes = new LinkedList<>(showtimes);
        this.theatre = theatre;
        this.delays = calculateDelays(showtimes);
    }

    private Map<Showtime, Duration> calculateDelays(Deque<Showtime> showtimes) {
        Map<Showtime, Duration> delays = new LinkedHashMap<>();
        Iterator<Showtime> iterator = showtimes.iterator();
        Showtime next = iterator.next();
        while (iterator.hasNext()) {
            Showtime current = next;
            next = iterator.next();
            DateTime endTime = current.getEndDateTime();
            DateTime nextStartTime = next.getStartDateTime();
            delays.put(current, new Duration(endTime, nextStartTime));
        }
        return delays;
    }

    public Duration getDelayAfterShowtime(Showtime showtime) {
        return delays.get(showtime);
    }

    public Deque<Showtime> getShowtimes() {
        return showtimes;
    }

    public Theatre getTheatre() {
        return theatre;
    }
}
