package org.onlinestore.model.product;

import org.onlinestore.model.money.Money;

public class ProductTestFactory {
    private static final int ENOUGH_ITEMS_IN_STOCK = Integer.MAX_VALUE;

    public static Product createTestProduct(Money price) {
        return createTestProduct(price, ENOUGH_ITEMS_IN_STOCK);
    }

    public static Product createTestProduct(Money price, int itemsInStock) {
        return new Product(
                ProductId.randomProductId(),
                "prod1",
                "prod1 desc",
                price,
                itemsInStock
        );
    }
}
