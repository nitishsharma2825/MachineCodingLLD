package models;

import java.util.List;

public class SeatHold {
    private String id;
    private List<String> seatIds;
    private long expiresAt;

    public SeatHold(String id, List<String> seatIds, long expiresAt) {
        this.id = id;
        this.seatIds = seatIds;
        this.expiresAt = expiresAt;
    }

    public String getId() {
        return id;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public long getExpiresAt() {
        return expiresAt;
    }
}
