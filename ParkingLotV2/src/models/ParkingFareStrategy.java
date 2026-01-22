package models;

public interface ParkingFareStrategy {
    public long calculateFair(long entryTime, long exitTime);
}
