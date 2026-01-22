package models;

public class CarParkingFareStrategy implements ParkingFareStrategy {
    private final int CAR_HOUR_RATE = 10;

    @Override
    public long calculateFair(long entryTime, long exitTime) {
        long duration = exitTime - entryTime;
        long hours = (duration) / (1000 * 60 * 60);
        return hours * CAR_HOUR_RATE;
    }
}
