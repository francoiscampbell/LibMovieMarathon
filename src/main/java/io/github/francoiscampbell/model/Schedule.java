package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.*;
import org.joda.time.*;

import java.util.*;

/**
 * Created by francois on 15-07-04.
 */
public class Schedule {
    private Theatre theatre;
    private Deque<Showtime> showtimes;
    private Map<Showtime, Duration> delays;
    private Duration totalDelay;

    public Schedule(Deque<Showtime> showtimes, Theatre theatre, boolean includePreviewsLength) {
        this.showtimes = new LinkedList<>(showtimes);
        this.theatre = theatre;
        this.delays = calculateDelays(showtimes, includePreviewsLength);
        this.totalDelay = calculateTotalDelay();
    }

    private Map<Showtime, Duration> calculateDelays(Deque<Showtime> showtimes, boolean includePreviewsLength) {
        if (showtimes.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Showtime, Duration> delays = new LinkedHashMap<>();
        Iterator<Showtime> iterator = showtimes.iterator();
        Showtime next = iterator.next();
        while (iterator.hasNext()) {
            Showtime current = next;
            next = iterator.next();
            DateTime endTime = current.getEndDateTime();
            DateTime nextStartTime = next.getStartDateTime(includePreviewsLength);
            delays.put(current, new Duration(endTime, nextStartTime));
        }
        return delays;
    }

    private Duration calculateTotalDelay() {
        Duration totalDelay = new Duration(0);
        for (Duration delay : delays.values()) {
            totalDelay = totalDelay.plus(delay);
        }
        return totalDelay;
    }

    public Map<Showtime, Duration> getDelays() {
        return delays;
    }

    public Duration getTotalDelay() {
        return totalDelay;
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
