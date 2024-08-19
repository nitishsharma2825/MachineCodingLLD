package services;

import models.*;

public class ParkingLotService {
    private ParkingLot parkingLot;
    public ParkingLotService(String id, int maxFloors, int maxSlotsPerFloor) {
        parkingLot = new ParkingLot(id, maxFloors, maxSlotsPerFloor);
    }

    public String parkVehicle(String vehicleType, String regNo, String color){
        VehicleType vehicleType1 = VehicleType.valueOf(vehicleType);
        Vehicle vehicle = new Vehicle(regNo, color, vehicleType1);

        String ticketId = "";
        boolean found = false;
        for (ParkingFloor parkingFloor : parkingLot.getFloors()) {
            for (ParkingSlot parkingSlot : parkingFloor.getSlots()) {
                if (parkingSlot.getSlotType().equals(vehicleType1) && !parkingSlot.isOccupied()) {
                    ticketId = parkingLot.getId()+"_"+parkingFloor.getId()+"_"+parkingSlot.getId();
                    parkingSlot.setOccupied(true);
                    assert parkingFloor.getSlotTypeToFreeMapping().get(vehicleType1) > 0;
                    parkingFloor.getSlotTypeToFreeMapping().put(vehicleType1, parkingFloor.getSlotTypeToFreeMapping().get(vehicleType1) - 1);
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        return ticketId;
    }

    public void unparkVehicle(String ticketId){
        String[] components = ticketId.split(" ");
        int floorNo = Integer.parseInt(components[1]);
        int slotNo = Integer.parseInt(components[2]);
        ParkingFloor parkingFloor = parkingLot.getFloors().get(floorNo);
        ParkingSlot parkingSlot = parkingFloor.getSlots().get(slotNo);
        parkingSlot.setOccupied(false);
        parkingFloor.getSlotTypeToFreeMapping().put(parkingSlot.getSlotType(), parkingFloor.getSlotTypeToFreeMapping().get(parkingSlot.getSlotType()) + 1);
    }

    public void display(String type, String vehicleType){
        VehicleType vehicleType1 = VehicleType.valueOf(vehicleType);
        switch (type) {
            case "free_count":
                for (ParkingFloor parkingFloor: parkingLot.getFloors()) {
                    int val = parkingFloor.getSlotTypeToFreeMapping().get(vehicleType1);
                    System.out.println("No of free slots for vehicle type "+type+" on floor "+parkingFloor.getId()+" : "+val);
                }
                break;
            case "free_slots":
                for (ParkingFloor parkingFloor: parkingLot.getFloors()) {
                    System.out.print("Free slots for vehicle type "+type+" on floor "+parkingFloor.getId()+" : ");
                    for (ParkingSlot parkingSlot: parkingFloor.getSlots()) {
                        if (parkingSlot.getSlotType().equals(vehicleType1) && !parkingSlot.isOccupied()) {
                            System.out.print(parkingSlot.getId()+", ");
                        }
                    }
                    System.out.println();
                }
                break;
            case "occupied_slots":
                for (ParkingFloor parkingFloor: parkingLot.getFloors()) {
                    System.out.print("Occupied slots for vehicle type "+type+" on floor "+parkingFloor.getId()+" : ");
                    for (ParkingSlot parkingSlot: parkingFloor.getSlots()) {
                        if (parkingSlot.getSlotType().equals(vehicleType1) && parkingSlot.isOccupied()) {
                            System.out.print(parkingSlot.getId()+", ");
                        }
                    }
                    System.out.println();
                }
                break;
            default:
                System.out.println("No such type");
        }
    }
}
