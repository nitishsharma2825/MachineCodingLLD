package org.onlinestore.model.cart;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.onlinestore.model.money.MoneyTestFactory;
import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductTestFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class CartTest {
    @Test
    void givenEmptyCart_addTwoProducts_numberOfItemsAndSubTotalIsCalculatedCorrectly()
            throws NotEnoughItemsInStockException {
        Cart cart = CartTestFactory.emptyCartForRandomCustomer();

        Product p1 = ProductTestFactory.createTestProduct(MoneyTestFactory.euros(12, 99));
        Product p2 = ProductTestFactory.createTestProduct(MoneyTestFactory.euros(5, 97));

        cart.addProduct(p1, 3);
        cart.addProduct(p2, 5);

        assertThat(cart.numberOfItems()).isEqualTo(8);
        assertThat(cart.subTotal()).isEqualTo(MoneyTestFactory.euros(68, 82));
    }

    @Test
    void givenAProductWithAFewItemsAvailable_addAllAvailableItemsToTheCart_succeeds() {
        Cart cart = CartTestFactory.emptyCartForRandomCustomer();
        Product product = ProductTestFactory.createTestProduct(MoneyTestFactory.euros(9, 97), 3);

        ThrowableAssert.ThrowingCallable invocation = () -> cart.addProduct(product, 3);

        assertThatNoException().isThrownBy(invocation);
    }
}
