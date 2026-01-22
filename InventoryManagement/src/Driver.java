import models.InventoryCountCannotBeNegativeException;
import models.InventoryManager;
import models.ProductId;
import models.WarehouseId;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws InventoryCountCannotBeNegativeException {
        Scanner sc = new Scanner(System.in);
        InventoryManager service = new InventoryManager(10);
        boolean exit = false;
        while(!exit) {
            String[] commands = sc.nextLine().split(" ");
            switch (commands[0].toLowerCase()) {
                case "addproduct":
                    WarehouseId warehouseId = new WarehouseId(commands[1]);
                    ProductId productId = new ProductId(commands[2]);
                    int amount = Integer.parseInt(commands[3]);
                    service.addStock(warehouseId, productId, amount);
                    break;
                case "removeproduct":
                    warehouseId = new WarehouseId(commands[1]);
                    productId = new ProductId(commands[2]);
                    amount = Integer.parseInt(commands[3]);
                    service.removeStock(warehouseId, productId, amount);
                    break;
                case "checkavailable":
                    warehouseId = new WarehouseId(commands[1]);
                    productId = new ProductId(commands[2]);
                    amount = Integer.parseInt(commands[3]);
                    boolean available = service.checkProductAvailability(warehouseId, productId, amount);
                    System.out.println("Availability Status: " + available);
                    break;
                case "exit":
                    exit = true;
                    break;
                case "addalert":
                    warehouseId = new WarehouseId(commands[1]);
                    productId = new ProductId(commands[2]);
                    int threshold = Integer.parseInt(commands[3]);
                    // construct an instance of AlertObserver depending on something [factory + strategy]
//                    service.addAlert(warehouseId, productId, threshold);
                    break;
                case "transferstock":
                    WarehouseId warehouseId1 = new WarehouseId(commands[1]);
                    WarehouseId warehouseId2 = new WarehouseId(commands[2]);
                    productId = new ProductId(commands[3]);
                    amount = Integer.parseInt(commands[4]);
                    service.transferProduct(warehouseId1, warehouseId2, productId, amount);
                default:
                    System.out.println("No such available command");
            }
        }
    }
}
