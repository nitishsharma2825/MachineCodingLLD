package org.onlineshop.adapter.out.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onlineshop.out.persistence.DemoProducts;
import org.onlinestore.application.port.out.persistence.ProductRepository;
import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductId;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractProductRepositoryTest<T extends ProductRepository> {
    private T productRepository;

    @BeforeEach
    void initRepository() {
        productRepository = createProductRepository();
    }

    protected abstract T createProductRepository();

    @Test
    void givenTestProductsAndTestProductId_findById_returnsATestProduct() {
        ProductId productId = DemoProducts.COMPUTER_MONITOR.id();

        Optional<Product> product = productRepository.findById(productId);

        assertThat(product).contains(DemoProducts.COMPUTER_MONITOR);
    }
}
