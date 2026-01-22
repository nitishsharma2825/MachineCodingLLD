package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private final WarehouseId warehouseId;
    private List<Product> products;
    private List<AlertConfig> alertConfigs;
    private Map<AlertConfigId, List<AlertObserver>> observers;
    public Warehouse() {
        this.warehouseId = WarehouseId.randomProductId();
        System.out.println(this.warehouseId);
        this.products = new ArrayList<>();
        this.alertConfigs = new ArrayList<>();
        this.observers = new HashMap<>();
    }
    public void addProduct(ProductId productId, int amount) throws InventoryCountCannotBeNegativeException {
        Product product = this.getProduct(productId);
        if (product == null) {
            product = new Product(ProductId.randomProductId(), 0);
            this.products.add(product);
        }

        product.updateInventoryCount(amount);
        this.checkAlerts(product);
    }
    public void removeProduct(ProductId productId, int amount) throws InventoryCountCannotBeNegativeException {
        Product product = this.getProduct(productId);
        assert product != null;
        this.checkAlerts(product);
        if (product.updateInventoryCount(amount) == 0) {
            this.products.remove(product);
        }
    }
    public boolean checkProductAvailable(ProductId productId, int amount){
        Product product = this.getProduct(productId);
        assert product != null;
        return product.isProductAvailable(amount);
    }
    public void addAlert(ProductId productId, int threshold, AlertObserver observer){
        AlertConfig config = new AlertConfig(productId, threshold);
        this.alertConfigs.add(config);
        if(this.observers.get(config.getAlertConfigId()) == null){
            List<AlertObserver> observersList = new ArrayList<>();
            observersList.add(observer);
            this.observers.put(config.getAlertConfigId(), observersList);
        } else {
            List<AlertObserver> observersList = this.observers.get(config.getAlertConfigId());
            observersList.add(observer);
        }
    }
    public WarehouseId getWarehouseId() {
        return warehouseId;
    }
    private Product getProduct(ProductId productId){
        for(Product p: products){
            if(p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
    }
    private void checkAlerts(Product product){
        for(AlertConfig alertConfig: this.alertConfigs){
            if(product.getProductId().equals(alertConfig.getProductId()) && product.getInventoryCount() < alertConfig.getThreshold()){
                for(AlertObserver listener: this.observers.get(alertConfig.getAlertConfigId())){
                    listener.takeAction();
                }
            }
        }
    }
}
