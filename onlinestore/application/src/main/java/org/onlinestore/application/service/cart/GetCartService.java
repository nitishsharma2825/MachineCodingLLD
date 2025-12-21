package org.onlinestore.application.service.cart;

import org.onlinestore.application.port.in.cart.GetCartUseCase;
import org.onlinestore.application.port.out.persistence.CartRepository;
import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.customer.CustomerId;

import java.util.Objects;

public class GetCartService implements GetCartUseCase {
    private final CartRepository cartRepository;

    public GetCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCart(CustomerId customerId) {
        Objects.requireNonNull(customerId, "'customerId' must not be null");

        return cartRepository.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));
    }
}
