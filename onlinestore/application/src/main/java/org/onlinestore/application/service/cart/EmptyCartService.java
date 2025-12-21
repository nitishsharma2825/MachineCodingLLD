package org.onlinestore.application.service.cart;

import org.onlinestore.application.port.in.cart.EmptyCartUseCase;
import org.onlinestore.application.port.out.persistence.CartRepository;
import org.onlinestore.model.customer.CustomerId;

import java.util.Objects;

public class EmptyCartService implements EmptyCartUseCase {
    private final CartRepository cartRepository;

    public EmptyCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void emptyCart(CustomerId customerId) {
        Objects.requireNonNull(customerId, "'customerId' must not be null");

        cartRepository.deleteById(customerId);
    }
}
