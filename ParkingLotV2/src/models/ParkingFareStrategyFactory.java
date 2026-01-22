package models;

public final class ParkingFareStrategyFactory {
    public static ParkingFareStrategy getParkingFareStrategy(SpotType spotType){
        switch (spotType) {
            case Truck -> {
                return new TruckParkingFareStrategy();
            }
            case Bike -> {
                return new BikeParkingFareStrategy();
            }
            case Car -> {
                return new CarParkingFareStrategy();
            }
            default -> {
                throw new IllegalArgumentException("unknown spot type, no parking fare strategy available!");
            }
        }
    }
}
