package org.onlineshop.out.persistence.inmemory;

import org.onlinestore.application.port.out.persistence.CartRepository;
import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.customer.CustomerId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCartRepository implements CartRepository {
    private final Map<CustomerId, Cart> carts = new ConcurrentHashMap<>();
    @Override
    public Optional<Cart> findByCustomerId(CustomerId customerId) {
        return Optional.ofNullable(carts.get(customerId));
    }

    @Override
    public void save(Cart cart) {
        carts.put(cart.id(), cart);
    }

    @Override
    public void deleteById(CustomerId customerId) {
        carts.remove(customerId);
    }
}
