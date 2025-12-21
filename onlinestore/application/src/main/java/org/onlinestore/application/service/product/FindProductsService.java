package org.onlinestore.application.service.product;

import org.onlinestore.application.port.in.product.FindProductsUseCase;
import org.onlinestore.application.port.out.persistence.ProductRepository;
import org.onlinestore.model.product.Product;

import java.util.List;
import java.util.Objects;

public class FindProductsService implements FindProductsUseCase {
    private final ProductRepository productRepository;

    public FindProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findByNameOrDescription(String query) {
        Objects.requireNonNull(query, "'query' must not be null");
        if (query.length() < 2) {
            throw new IllegalArgumentException("'query' must >=2 chars long");
        }

        return productRepository.findByNameOrDescription(query);
    }
}
