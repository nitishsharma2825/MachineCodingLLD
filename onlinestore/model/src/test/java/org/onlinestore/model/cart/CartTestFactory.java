package org.onlinestore.model.cart;

import org.onlinestore.model.customer.CustomerId;

import java.util.concurrent.ThreadLocalRandom;

public class CartTestFactory {
    public static Cart emptyCartForRandomCustomer() {
        return new Cart(new CustomerId(ThreadLocalRandom.current().nextInt(1_000_000)));
    }
}
