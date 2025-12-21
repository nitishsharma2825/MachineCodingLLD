package org.onlinestore.application.port.in.cart;

import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.cart.NotEnoughItemsInStockException;
import org.onlinestore.model.customer.CustomerId;
import org.onlinestore.model.product.ProductId;

public interface AddToCartUseCase {
    Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
            throws ProductNotFoundException, NotEnoughItemsInStockException;
}
