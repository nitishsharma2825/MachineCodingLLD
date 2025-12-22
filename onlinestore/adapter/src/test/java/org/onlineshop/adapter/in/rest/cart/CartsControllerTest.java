package org.onlineshop.adapter.in.rest.cart;

import io.restassured.response.Response;
import jakarta.ws.rs.core.Application;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.onlineshop.in.rest.cart.AddToCartController;
import org.onlineshop.in.rest.cart.EmptyCartController;
import org.onlineshop.in.rest.cart.GetCartController;
import org.onlinestore.application.port.in.cart.AddToCartUseCase;
import org.onlinestore.application.port.in.cart.EmptyCartUseCase;
import org.onlinestore.application.port.in.cart.GetCartUseCase;
import org.onlinestore.application.port.in.cart.ProductNotFoundException;
import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.cart.NotEnoughItemsInStockException;
import org.onlinestore.model.customer.CustomerId;
import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductId;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onlineshop.adapter.in.rest.HttpTestCommons.TEST_PORT;
import static org.onlineshop.adapter.in.rest.HttpTestCommons.assertThatResponseIsError;
import static org.onlineshop.adapter.in.rest.cart.CartsControllerAssertions.assertThatResponseIsCart;
import static org.onlinestore.model.money.MoneyTestFactory.euros;
import static org.onlinestore.model.product.ProductTestFactory.createTestProduct;

public class CartsControllerTest {
    private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

    private static final AddToCartUseCase addToCartUseCase = mock(AddToCartUseCase.class);
    private static final GetCartUseCase getCartUseCase = mock(GetCartUseCase.class);
    private static final EmptyCartUseCase emptyCartUseCase = mock(EmptyCartUseCase.class);

    private static UndertowJaxrsServer server;

    @BeforeAll
    static void init() {
        server =
                new UndertowJaxrsServer()
                        .setPort(TEST_PORT)
                        .start()
                        .deploy(
                                new Application() {
                                    @Override
                                    public Set<Object> getSingletons() {
                                        return Set.of(
                                                new AddToCartController(addToCartUseCase),
                                                new GetCartController(getCartUseCase),
                                                new EmptyCartController(emptyCartUseCase));
                                    }
                                });
    }

    @AfterAll
    static void stop() {
        server.stop();
    }

    @Test
    void givenSomeTestData_addLineItem_invokesAddToCartUseCaseAndReturnsUpdatedCart() throws NotEnoughItemsInStockException, ProductNotFoundException {
        // Arrange
        CustomerId customerId = TEST_CUSTOMER_ID;
        ProductId productId = TEST_PRODUCT_1.id();
        int quantity = 5;

        Cart cart = new Cart(customerId);
        cart.addProduct(TEST_PRODUCT_1, quantity);

        when(addToCartUseCase.addToCart(customerId, productId, quantity)).thenReturn(cart);

        // Act
        Response response = given()
                .port(TEST_PORT)
                .queryParam("productId", productId.value())
                .queryParam("quantity", quantity)
                .post("/carts/" + customerId.value() + "/line-items")
                .then()
                .extract()
                .response();

        // Assert
        assertThatResponseIsCart(response, cart);
    }
}
