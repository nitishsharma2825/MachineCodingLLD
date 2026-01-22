package models;

public class BikeParkingFareStrategy implements ParkingFareStrategy {
    private final int BIKE_HOUR_RATE = 5;

    @Override
    public long calculateFair(long entryTime, long exitTime) {
        long duration = exitTime - entryTime;
        long hours = (duration) / (1000 * 60 * 60);
        return hours * BIKE_HOUR_RATE;
    }
}
