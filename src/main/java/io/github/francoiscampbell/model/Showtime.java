package io.github.francoiscampbell.model;

import io.github.francoiscampbell.apimodel.ApiShowtime;

import java.util.Date;

/**
 * Created by francois on 15-07-03.
 */
public class Showtime {
    private Date dateTime;

    public Showtime(Date dateTime){
        this.dateTime = dateTime;
    }

    public Showtime(ApiShowtime apiShowtime){
        this(apiShowtime.getDateTime());
    }
}
