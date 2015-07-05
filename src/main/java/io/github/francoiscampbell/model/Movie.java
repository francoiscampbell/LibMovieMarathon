package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.ApiMovie;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.Objects;

/**
 * Created by francois on 15-07-03.
 */
public class Movie {
    private String id;
    private String title;
    private Duration runningLength;
    private Duration previewsLength;

    public Movie(String runningLength, String title, String id) {
        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();
        builder.appendPrefix("PT")
               .appendHours()
               .appendSuffix("H")
               .appendMinutes()
               .appendSuffix("M");
        PeriodFormatter formatter = builder.toFormatter();
        Period p = formatter.parsePeriod(runningLength);
        this.runningLength = p.toStandardDuration();
        this.previewsLength = Duration.standardMinutes(15); //default 15 mins of previews
        this.title = title;
        this.id = id;
    }

    public Movie(ApiMovie apiMovie) {
        this(apiMovie.getRunTime(), apiMovie.getTitle(), apiMovie.getTmsId());
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningLength() {
        return runningLength;
    }

    public Duration getPreviewsLength() {
        return previewsLength;
    }

    public Duration getTotalLength() {
        return getRunningLength().plus(getPreviewsLength());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return Objects.equals(id, movie.id);

    }

    @Override
    public String toString() {
        return title;
    }
}
