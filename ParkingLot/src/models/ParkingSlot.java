package models;

public class ParkingSlot {
    private int id;
    private boolean occupied;
    private VehicleType slotType;

    public ParkingSlot(int id, boolean occupied, VehicleType slotType) {
        this.id = id;
        this.occupied = occupied;
        this.slotType = slotType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public VehicleType getSlotType() {
        return slotType;
    }

    public void setSlotType(VehicleType slotType) {
        this.slotType = slotType;
    }
}
