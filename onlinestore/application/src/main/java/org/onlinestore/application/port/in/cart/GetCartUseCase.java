package org.onlinestore.application.port.in.cart;

import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.customer.CustomerId;

public interface GetCartUseCase {
    Cart getCart(CustomerId customerId);
}
