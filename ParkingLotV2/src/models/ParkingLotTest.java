package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParkingLotTest {
    private ParkingLot parkingLot;
    private TicketId ticketId;
    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot(10);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void assignSpot() {
        VehicleType vehicleType = VehicleType.BIKE;
        try {
            Ticket ticket = this.parkingLot.assignSpot(vehicleType);
            this.ticketId = ticket.getTicketId();
            Assertions.assertNotNull(ticket);
        } catch (NoParkingSpotAvailableException ignored) {
        }
    }

    @Test
    void generateFair() {
        try {
            long fare = this.parkingLot.generateFair(this.ticketId);
            Assertions.assertEquals(fare, 0);
        } catch (InvalidTicketException ignored) {}
    }
}