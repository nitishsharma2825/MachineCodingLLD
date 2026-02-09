package models;

import java.util.List;

public class Reservation {
    private String id;
    private Showtime showtime;
    private List<String> seats;

    public Reservation(String id, Showtime showtime, List<String> seats) {
        this.id = id;
        this.showtime = showtime;
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public List<String> getSeats() {
        return seats;
    }
}
