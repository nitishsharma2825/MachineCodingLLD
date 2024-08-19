package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingFloor {
    private int id;
    private int maxSlots;
    private HashMap<VehicleType, Integer> slotTypeToFreeMapping;
    private List<ParkingSlot> slots;
    public ParkingFloor(int id, int maxSlots) {
        this.id = id;
        this.maxSlots = maxSlots;
        this.slotTypeToFreeMapping = new HashMap<>();
        this.slots = new ArrayList<>();
        this.slots.add(new ParkingSlot(1, false, VehicleType.TRUCK));
        this.slotTypeToFreeMapping.put(VehicleType.TRUCK, 1);
        this.slots.add(new ParkingSlot(2, false, VehicleType.BIKE));
        this.slots.add(new ParkingSlot(3, false, VehicleType.BIKE));
        this.slotTypeToFreeMapping.put(VehicleType.BIKE, 2);
        for(int i=4;i<=maxSlots;i++){
            this.slots.add(new ParkingSlot(i, false, VehicleType.CAR));
        }
        this.slotTypeToFreeMapping.put(VehicleType.CAR, maxSlots-3);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public void setMaxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
    }

    public HashMap<VehicleType, Integer> getSlotTypeToFreeMapping() {
        return slotTypeToFreeMapping;
    }

    public void setSlotTypeToFreeMapping(HashMap<VehicleType, Integer> slotTypeToFreeMapping) {
        this.slotTypeToFreeMapping = slotTypeToFreeMapping;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<ParkingSlot> slots) {
        this.slots = slots;
    }
}
