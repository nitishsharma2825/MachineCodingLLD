package org.onlineshop.in.rest.product;

import org.onlinestore.model.money.Money;
import org.onlinestore.model.product.Product;

public record ProductInListWebModel(String id, String name, Money price, int itemsInStock) {
    public static ProductInListWebModel fromDomainModel(Product product) {
        return new ProductInListWebModel(product.id().value(), product.name(), product.price(), product.itemsInStock());
    }
}
