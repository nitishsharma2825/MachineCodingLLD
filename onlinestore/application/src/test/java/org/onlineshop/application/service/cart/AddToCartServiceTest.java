package org.onlineshop.application.service.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onlinestore.application.port.in.cart.ProductNotFoundException;
import org.onlinestore.application.port.out.persistence.CartRepository;
import org.onlinestore.application.port.out.persistence.ProductRepository;
import org.onlinestore.application.service.cart.AddToCartService;
import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.cart.NotEnoughItemsInStockException;
import org.onlinestore.model.customer.CustomerId;
import org.onlinestore.model.money.MoneyTestFactory;
import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductTestFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AddToCartServiceTest {
    private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
    private static final Product TEST_PRODUCT_1 = ProductTestFactory.createTestProduct(MoneyTestFactory.euros(19, 99));
    private static final Product TEST_PRODUCT_2 = ProductTestFactory.createTestProduct(MoneyTestFactory.euros(25, 99));

    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);

    private final AddToCartService addToCartService = new AddToCartService(cartRepository, productRepository);

    @BeforeEach
    void initTestDoubles() {
        when(productRepository.findById(TEST_PRODUCT_1.id())).thenReturn(Optional.of(TEST_PRODUCT_1));
        when(productRepository.findById(TEST_PRODUCT_2.id())).thenReturn(Optional.of(TEST_PRODUCT_2));
    }

    @Test
    void givenExistingCart_addToCart_cartWithAddedProductsIsSavedAndReturned()
        throws NotEnoughItemsInStockException, ProductNotFoundException {
        // Arrange
        Cart persistedCart = new Cart(TEST_CUSTOMER_ID);
        persistedCart.addProduct(TEST_PRODUCT_1, 1);

        when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Optional.of(persistedCart));

        // Act
        Cart cart = addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_2.id(), 3);

        // Assert
        verify(cartRepository).save(cart);

        assertThat(cart.lineItems()).hasSize(2);
        assertThat(cart.lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_1);
        assertThat(cart.lineItems().get(0).quantity()).isEqualTo(1);
        assertThat(cart.lineItems().get(1).product()).isEqualTo(TEST_PRODUCT_2);
        assertThat(cart.lineItems().get(1).quantity()).isEqualTo(3);
    }
}
