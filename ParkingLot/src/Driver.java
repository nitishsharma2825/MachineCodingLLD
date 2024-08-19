import models.VehicleType;
import services.ParkingLotService;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParkingLotService parkingLotService = null;
        boolean exit = false;

        while (!exit) {
            String[] commands = sc.nextLine().split(" ");
            switch (commands[0].toLowerCase()) {
                case "create_parking_lot":
                    parkingLotService = new ParkingLotService(commands[2], Integer.parseInt(commands[3]), Integer.parseInt(commands[4]));
                    break;
                case "park_vehicle":
                    assert parkingLotService != null;
                    String ticketId = parkingLotService.parkVehicle(commands[2], commands[3], commands[4]);
                    System.out.println(ticketId);
                    break;
                case "unpark_vehicle":
                    assert parkingLotService != null;
                    parkingLotService.unparkVehicle(commands[2]);
                    break;
                case "display":
                    assert parkingLotService != null;
                    parkingLotService.display(commands[2], commands[3]);
                    break;
                default:
                    exit = true;
                    System.out.println("No such command!");
            }
        }
    }
}
