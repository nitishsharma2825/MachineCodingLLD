package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class BookingSystem {
    private List<Theater> theaters;

    // extra indexes
    // Map<MovieId, Movie>
    // Map<MovieId, []Showtime>
    // Map<ShowtimeId, Showtime>
    // Map<ReservationId, Reservation>

    public BookingSystem(List<Theater> theaters) {
        this.theaters = theaters;
    }

    public List<Showtime> searchMovies(String title) {
        if(title == null || title.isEmpty()) {
            return new ArrayList<>();
        }

        String searchLower = title.toLowerCase();
        List<Showtime> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for(Theater theater: this.theaters){
            for(Showtime showtime: theater.getShowtimes()){
                Movie movie = showtime.getMovie();
                if(movie.title().contains(searchLower) && showtime.getEventTime().isAfter(now)) {
                    result.add(showtime);
                }
            }
        }

        return result;
    }

    public List<Showtime> getShowtimesAtTheater(Theater theater) {
        if (theater == null) {
            return new ArrayList<>();
        }

        List<Showtime> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for(Showtime showtime: theater.getShowtimes()) {
            if(showtime.getEventTime().isAfter(now)) {
                result.add(showtime);
            }
        }

        return result;
    }

    public Reservation book(String showtimeId, List<String> seatIds) {
        if(showtimeId == null || seatIds == null){
            throw new IllegalArgumentException("Invalid booking request");
        }

        Showtime showtime = null;
        for(Theater theater: this.theaters){
            for(Showtime s: theater.getShowtimes()){
                if(s.getId().equals(showtimeId)) {
                    showtime = s;
                    break;
                }
            }
        }

        if(showtime == null) throw new NoSuchElementException("Show time not found");

        Reservation reservation = new Reservation(
                UUID.randomUUID().toString(),
                showtime,
                seatIds
        );

        showtime.book(reservation);

        return reservation;
    }

    public void cancelReservation(String confirmationid) {
        Reservation reservation = null;
        for(Theater theater: this.theaters){
            for(Showtime s: theater.getShowtimes()){
                for(Reservation r: s.getReservations()) {
                    if (r.getId().equals(confirmationid)) {
                        reservation = r;
                        break;
                    }
                }
            }
        }

        if (reservation == null) throw new IllegalArgumentException("Invalid confirmation id");
        Showtime s = reservation.getShowtime();
        s.cancel(reservation);
    }
}
