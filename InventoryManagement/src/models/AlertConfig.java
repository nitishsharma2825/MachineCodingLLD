package models;

public class AlertConfig {
    private AlertConfigId alertConfigId;
    private int threshold;
    private ProductId productId;

    public AlertConfig(ProductId productId, int threshold) {
        this.alertConfigId = AlertConfigId.randomProductId();
        this.threshold = threshold;
        this.productId = productId;
    }

    public AlertConfigId getAlertConfigId() {
        return alertConfigId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getThreshold() {
        return threshold;
    }
}
