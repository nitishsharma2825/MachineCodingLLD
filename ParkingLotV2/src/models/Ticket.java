package models;

public class Ticket {
    private TicketId ticketId;
    private ParkingSpotId parkingSpotId;
    private long entryTimeInMs;

    public Ticket(ParkingSpotId parkingSpotId, long entryTimeInMs) {
        this.parkingSpotId = parkingSpotId;
        this.entryTimeInMs = entryTimeInMs;
        this.ticketId = TicketId.randomTicketId();
    }

    public TicketId getTicketId() {
        return ticketId;
    }

    public ParkingSpotId getParkingSpotId() {
        return parkingSpotId;
    }

    public long getEntryTimeInMs() {
        return entryTimeInMs;
    }
}
