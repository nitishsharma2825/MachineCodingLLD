package org.onlineshop.adapter.out.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onlinestore.application.port.out.persistence.CartRepository;
import org.onlinestore.application.port.out.persistence.ProductRepository;
import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.customer.CustomerId;
import org.onlinestore.model.product.Product;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.onlinestore.model.money.MoneyTestFactory.euros;
import static org.onlinestore.model.product.ProductTestFactory.createTestProduct;

public abstract class AbstractCartRepositoryTest <T extends CartRepository, U extends ProductRepository> {
    private T cartRepository;

    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(1, 49));
    private static final AtomicInteger CUSTOMER_ID_SEQUENCE_GENERATOR = new AtomicInteger();

    @BeforeEach
    void initRepositories() {
        cartRepository = createCartRepository();
        persistTestProducts();
    }
    protected abstract T createCartRepository();
    private void persistTestProducts() {
        U productRepository = createProductRepository();
        productRepository.save(TEST_PRODUCT_1);
        productRepository.save(TEST_PRODUCT_2);
    }
    protected abstract U createProductRepository();

    @Test
    void givenACustomerIDForWhichNoCartIsPersisted_findByCustomerId_returnsAnEmptyOptional() {
        CustomerId customerId = createUniqueCustomerId();
        Optional<Cart> cart = cartRepository.findByCustomerId(customerId);
        assertThat(cart).isEmpty();
    }
    private static CustomerId createUniqueCustomerId() {
        return new CustomerId(CUSTOMER_ID_SEQUENCE_GENERATOR.incrementAndGet());
    }
}
