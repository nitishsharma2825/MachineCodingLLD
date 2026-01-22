import models.*;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws NoParkingSpotAvailableException, InvalidTicketException {
        Scanner sc = new Scanner(System.in);
        ParkingLot parkingLot = new ParkingLot(10);
        boolean exit = false;
        while(!exit) {
            String[] commands = sc.nextLine().split(" ");
            switch (commands[0].toLowerCase()) {
                case "entry":
                    VehicleType vehicleType = VehicleType.valueOf(commands[1].toUpperCase());
                    Ticket ticket = parkingLot.assignSpot(vehicleType);
                    System.out.println("Your ticket id is: " + ticket.getTicketId());
                    break;
                case "exit":
                    TicketId ticketId = new TicketId(commands[1]);
                    long fare = parkingLot.generateFair(ticketId);
                    System.out.println("Your fair is: " + fare);
                case "end":
                    exit = true;
            }
        }
    }
}
