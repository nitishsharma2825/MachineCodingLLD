package models;

public class InventoryCountCannotBeNegativeException extends Exception {
    public InventoryCountCannotBeNegativeException(String message) {
        super(message);
    }
}
