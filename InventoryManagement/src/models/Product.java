package models;

public class Product {
    private ProductId productId;
    private int inventoryCount;

    public Product(ProductId productId, int inventoryCount) {
        this.productId = productId;
        this.inventoryCount = inventoryCount;
    }

    public int updateInventoryCount(int value) throws InventoryCountCannotBeNegativeException {
        if((this.inventoryCount - value) < 0){
            throw new InventoryCountCannotBeNegativeException("Inventory count for product id: " + productId.id() + " can't be negative");
        }
        this.inventoryCount += value;
        return this.inventoryCount;
    }

    public boolean isProductAvailable(int amount) {
        return this.inventoryCount > amount;
    }
    public ProductId getProductId() {
        return productId;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }
}
