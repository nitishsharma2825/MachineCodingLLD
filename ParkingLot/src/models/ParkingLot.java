package models;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private String id;
    private List<ParkingFloor> floors;
    private int maxFloors;
    private int maxSlotsPerFloor;

    public ParkingLot(String id, int maxFloors, int maxSlotsPerFloor) {
        this.id = id;
        this.maxFloors = maxFloors;
        this.floors = new ArrayList<>();
        for(int i=0;i<maxFloors;i++){
            this.floors.add(new ParkingFloor(i+1, maxSlotsPerFloor));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ParkingFloor> getFloors() {
        return floors;
    }

    public void setFloors(List<ParkingFloor> floors) {
        this.floors = floors;
    }

    public int getMaxFloors() {
        return maxFloors;
    }

    public void setMaxFloors(int maxFloors) {
        this.maxFloors = maxFloors;
    }

    public int getMaxSlotsPerFloor() {
        return maxSlotsPerFloor;
    }

    public void setMaxSlotsPerFloor(int maxSlotsPerFloor) {
        this.maxSlotsPerFloor = maxSlotsPerFloor;
    }
}
