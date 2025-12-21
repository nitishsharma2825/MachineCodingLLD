package org.onlinestore.application.service.cart;

import org.onlinestore.application.port.in.cart.AddToCartUseCase;
import org.onlinestore.application.port.in.cart.ProductNotFoundException;
import org.onlinestore.application.port.out.persistence.CartRepository;
import org.onlinestore.application.port.out.persistence.ProductRepository;
import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.cart.NotEnoughItemsInStockException;
import org.onlinestore.model.customer.CustomerId;
import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductId;

import java.util.Objects;

public class AddToCartService implements AddToCartUseCase {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public AddToCartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addToCart(CustomerId customerId, ProductId productId, int quantity) throws ProductNotFoundException, NotEnoughItemsInStockException {
        Objects.requireNonNull(customerId, "'customerId' must not be null");
        Objects.requireNonNull(productId, "'productId' must not be null");
        if (quantity < 1) {
            throw new IllegalArgumentException("'quantity' must be > 0");
        }

        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        Cart cart = cartRepository.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));
        cart.addProduct(product, quantity);
        cartRepository.save(cart);
        return cart;
    }
}
