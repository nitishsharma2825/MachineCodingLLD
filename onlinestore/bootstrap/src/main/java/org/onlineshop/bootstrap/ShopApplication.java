package org.onlineshop.bootstrap;

import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.core.Application;
import org.onlineshop.in.rest.cart.AddToCartController;
import org.onlineshop.in.rest.cart.EmptyCartController;
import org.onlineshop.in.rest.cart.GetCartController;
import org.onlineshop.in.rest.product.FindProductsController;
import org.onlineshop.out.persistence.inmemory.InMemoryCartRepository;
import org.onlineshop.out.persistence.inmemory.InMemoryProductRepository;
import org.onlineshop.out.persistence.mysql.EntityManagerFactoryFactory;
import org.onlineshop.out.persistence.mysql.MySQLCartRepository;
import org.onlineshop.out.persistence.mysql.MySQLProductRepository;
import org.onlinestore.application.port.in.cart.AddToCartUseCase;
import org.onlinestore.application.port.in.cart.EmptyCartUseCase;
import org.onlinestore.application.port.in.cart.GetCartUseCase;
import org.onlinestore.application.port.in.product.FindProductsUseCase;
import org.onlinestore.application.port.out.persistence.CartRepository;
import org.onlinestore.application.port.out.persistence.ProductRepository;
import org.onlinestore.application.service.cart.AddToCartService;
import org.onlinestore.application.service.cart.EmptyCartService;
import org.onlinestore.application.service.cart.GetCartService;
import org.onlinestore.application.service.product.FindProductsService;

import java.util.Set;

public class ShopApplication extends Application {
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    @Override
    public Set<Object> getSingletons() {
        initPersistenceAdapters();
        return Set.of(
                addToCartController(),
                getCartController(),
                emptyCartController(),
                findProductsController()
        );
    }

    private void initPersistenceAdapters() {
        String persistence = System.getProperty("persistence", "inmemory");
        switch (persistence) {
            case "inmemory" -> initInMemoryAdapters();
            case "mysql" -> initMySQLAdapters();
            default -> throw new IllegalArgumentException("Invalid persistence property");
        }
    }

    private void initInMemoryAdapters() {
        cartRepository = new InMemoryCartRepository();
        productRepository = new InMemoryProductRepository();
    }

    private void initMySQLAdapters() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryFactory.createMySQLEntityManagerFactory(
          "jdbc:mysql://localhost:3306/shop", "root", "test"
        );
        cartRepository = new MySQLCartRepository(entityManagerFactory);
        productRepository = new MySQLProductRepository(entityManagerFactory);
    }

    private AddToCartController addToCartController() {
        AddToCartUseCase addToCartUseCase = new AddToCartService(cartRepository, productRepository);
        return new AddToCartController(addToCartUseCase);
    }

    private GetCartController getCartController() {
        GetCartUseCase getCartUseCase = new GetCartService(cartRepository);
        return new GetCartController(getCartUseCase);
    }

    private EmptyCartController emptyCartController() {
        EmptyCartUseCase emptyCartUseCase = new EmptyCartService(cartRepository);
        return new EmptyCartController(emptyCartUseCase);
    }

    private FindProductsController findProductsController() {
        FindProductsUseCase findProductsUseCase = new FindProductsService(productRepository);
        return new FindProductsController(findProductsUseCase);
    }
}
