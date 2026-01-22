package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingLot {
    private int numSpots;
    private ParkingLotId parkingLotId;
    private List<Ticket> allTickets;
    private List<ParkingSpot> allParkingSpots;
    private HashSet<ParkingSpotId> parkingSpotsOccupied;
    private ReentrantLock lock = new ReentrantLock();
    public ParkingLot(int numSpots) {
        this.parkingLotId = ParkingLotId.randomParkingLotId();
        this.allTickets = new ArrayList<>();
        this.allParkingSpots = new ArrayList<>();
        this.numSpots = numSpots;
        this.parkingSpotsOccupied = new HashSet<>();
        this.addParkingSpots();
    }

    public Ticket assignSpot(VehicleType vehicleType) throws NoParkingSpotAvailableException {
        lock.lock();
        try {
            ParkingSpotId parkingSpotId = null;
            SpotType spotType = this.convertVehicleToSpotType(vehicleType);
            for(ParkingSpot parkingSpot: this.allParkingSpots){
                if(parkingSpot.spotType().equals(spotType) && !this.parkingSpotsOccupied.contains(parkingSpot.parkingSpotId())){
                    parkingSpotId = parkingSpot.parkingSpotId();
                    break;
                }
            }

            if (parkingSpotId == null) {
                throw new NoParkingSpotAvailableException("No spot available!");
            }

            this.parkingSpotsOccupied.add(parkingSpotId);
            Ticket ticket = new Ticket(parkingSpotId, System.currentTimeMillis());
            this.allTickets.add(ticket);
            return ticket;
        } finally {
            lock.unlock();
        }
    }

    public long generateFair(TicketId ticketId) throws InvalidTicketException {
        Ticket curTicket = null;
        for(Ticket ticket: this.allTickets){
            if(ticket.getTicketId().equals(ticketId)){
                curTicket = ticket;
                break;
            }
        }

        if (curTicket == null) {
            throw new InvalidTicketException("ticket is invalid");
        }

        SpotType spotType = SpotType.Bike;
        for(ParkingSpot parkingSpot: this.allParkingSpots){
            if(parkingSpot.parkingSpotId().equals(curTicket.getParkingSpotId())){
                spotType = parkingSpot.spotType();
                break;
            }
        }

        ParkingFareStrategy fareStrategy = ParkingFareStrategyFactory.getParkingFareStrategy(spotType);
        return fareStrategy.calculateFair(curTicket.getEntryTimeInMs(), System.currentTimeMillis());
    }
    private SpotType convertVehicleToSpotType(VehicleType vehicleType) {
        switch (vehicleType) {
            case CAR -> {
                return SpotType.Car;
            }
            case BIKE -> {
                return SpotType.Bike;
            }
            case TRUCK -> {
                return SpotType.Truck;
            }
            default -> {
                throw new IllegalArgumentException("no vehicle type found");
            }
        }
    }

    private void addParkingSpots(){
        this.allParkingSpots.add(new ParkingSpot(ParkingSpotId.randomParkingSpotId(), SpotType.Car));
        this.allParkingSpots.add(new ParkingSpot(ParkingSpotId.randomParkingSpotId(), SpotType.Truck));
        this.allParkingSpots.add(new ParkingSpot(ParkingSpotId.randomParkingSpotId(), SpotType.Bike));
    }
}
