package org.onlinestore.application.port.in.cart;

import org.onlinestore.model.customer.CustomerId;

public interface EmptyCartUseCase {
    void emptyCart(CustomerId customerId);
}
