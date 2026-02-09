package models;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Showtime {
    private String id;
    private Movie movie;
    private LocalDateTime eventTime;
    private String screenLabel;
    private List<Reservation> reservations;
    private ReentrantLock lock = new ReentrantLock();
    private Map<String, SeatHold> holds;

    public Showtime(String id, Movie movie, LocalDateTime eventTime, String screenLabel, List<Reservation> reservations) {
        this.id = id;
        this.movie = movie;
        this.eventTime = eventTime;
        this.screenLabel = screenLabel;
        this.reservations = reservations;
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public String getScreenLabel() {
        return screenLabel;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void book(Reservation reservation) {
        lock.lock();
        try {
            List<String> seatIds = reservation.getSeats();
            for(String seatId: seatIds) {
                if(!isValidSeat(seatId)) {
                    throw new IllegalArgumentException("Invalid seat: " + seatId);
                }
            }

            for (String seatId: seatIds) {
                if(!isAvailable(seatId)) {
                    throw new IllegalStateException("Seat " + seatId + "is not available");
                }
            }

            reservations.add(reservation);
        } finally {
            lock.unlock();
        }
    }

    private boolean isValidSeat(String seatId) {
        char row = seatId.charAt(0);
        int num = Integer.parseInt(seatId.substring(1));
        return row >= 'A' && row <= 'Z' && num >=0 && num <= 20;
    }

    private boolean isAvailable(String seatId) {
        for(Reservation reservation: reservations) {
            if(reservation.getSeats().contains(seatId)) {
                return false;
            }
        }

        long now = System.currentTimeMillis();
        for (SeatHold hold: holds.values()) {
            if(hold.getSeatIds().contains(seatId) && hold.getExpiresAt() > now) {
                return false;
            }
        }
        return true;
    }

    public List<String> getAvailableSeats() {
        Set<String> booked = new HashSet<>();
        for(Reservation reservation: reservations) {
            booked.addAll(reservation.getSeats());
        }

        List<String> available = new ArrayList<>();
        for(char row = 'A'; row<='Z';row++){
            for(int num = 0; num <= 20; num++){
                String seatId = ""+row+num;
                if(!booked.contains(seatId)) {
                    available.add(seatId);
                }
            }
        }
        return available;
    }

    public void cancel(Reservation reservation) {
        lock.lock();
        reservations.remove(reservation);
        lock.unlock();
    }

    // called after seat selection but before payment, during checkout
    public SeatHold holdSeats(List<String> seatIds, long timeoutMs) throws SeatUnavailableException {
        lock.lock();
        for(String seat: seatIds){
            if(!isAvailable(seat)) {
                throw new SeatUnavailableException("Seat is not available");
            }
        }

        SeatHold hold = new SeatHold(UUID.randomUUID().toString(), seatIds, timeoutMs);
        holds.put(hold.getId(), hold);
        lock.unlock();
        return hold;
    }

    // called after payment is completed, moved the hold seats to reserved seats
    public void confirmHold(String holdId, Reservation reservation) {
        lock.lock();
        SeatHold hold = holds.get(holdId);

        if (System.currentTimeMillis() > hold.getExpiresAt()) {
            throw new IllegalStateException("Hold has expired, book again");
        }

        holds.remove(holdId);
        reservations.add(reservation);
        lock.unlock();
    }

    public void cleanExpiredHolds() {
        lock.lock();
        for(SeatHold hold: holds.values()){
            if(System.currentTimeMillis() > hold.getExpiresAt()){
                holds.remove(hold.getId());
            }
        }
        lock.unlock();
    }
}
