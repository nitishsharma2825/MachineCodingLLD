package models;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private List<Warehouse> warehouseList;
    public InventoryManager(int count) {
        this.warehouseList = new ArrayList<>();
        this.warehouseList.add(new Warehouse());
        this.warehouseList.add(new Warehouse());
    }
    public void addStock(WarehouseId warehouseId, ProductId productId, int amount) throws InventoryCountCannotBeNegativeException {
        Warehouse warehouse = this.getWarehouse(warehouseId);
        if (warehouse == null) throw new RuntimeException("no warehouse exists with this id");
        warehouse.addProduct(productId, amount);
    }
    public void removeStock(WarehouseId warehouseId, ProductId productId, int amount) throws InventoryCountCannotBeNegativeException {
        Warehouse warehouse = this.getWarehouse(warehouseId);
        if (warehouse == null) throw new RuntimeException("no warehouse exists with this id");
        warehouse.removeProduct(productId, amount);
    }
    public void transferProduct(WarehouseId sourceWarehouseId, WarehouseId destWarehouseId, ProductId productId, int amount) throws InventoryCountCannotBeNegativeException {
        Warehouse sourceWarehouse = this.getWarehouse(sourceWarehouseId);
        Warehouse destWarehouse = this.getWarehouse(destWarehouseId);

        if (sourceWarehouseId == null || destWarehouseId == null) throw new RuntimeException("no warehouse exists with this id");
        destWarehouse.addProduct(productId, amount);
        assert sourceWarehouse != null;
        sourceWarehouse.removeProduct(productId, amount);
    }
    public boolean checkProductAvailability(WarehouseId warehouseId, ProductId productId, int amt){
        Warehouse warehouse = this.getWarehouse(warehouseId);
        if (warehouse == null) throw new RuntimeException("no warehouse exists with this id");
        return warehouse.checkProductAvailable(productId, amt);
    }
    public void addAlert(WarehouseId warehouseId, ProductId productId, int threshold, AlertObserver alertObserver) {
        Warehouse warehouse = this.getWarehouse(warehouseId);
        if (warehouse == null) throw new RuntimeException("no warehouse exists with this id");
        warehouse.addAlert(productId, threshold, alertObserver);
    }

    private Warehouse getWarehouse(WarehouseId warehouseId) {
        for(Warehouse w: warehouseList){
            if(w.getWarehouseId().equals(warehouseId)){
                return w;
            }
        }

        return null;
    }
}
