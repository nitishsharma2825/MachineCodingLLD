package org.onlinestore.application.port.out.persistence;

import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.customer.CustomerId;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByCustomerId(CustomerId customerId);
    void save(Cart cart);
    void deleteById(CustomerId customerId);
}
