package models;

public class TruckParkingFareStrategy implements ParkingFareStrategy {
    private final int TRUCK_HOUR_RATE = 5;

    @Override
    public long calculateFair(long entryTime, long exitTime) {
        long duration = exitTime - entryTime;
        long hours = (duration) / (1000 * 60 * 60);
        return hours * TRUCK_HOUR_RATE;
    }
}
