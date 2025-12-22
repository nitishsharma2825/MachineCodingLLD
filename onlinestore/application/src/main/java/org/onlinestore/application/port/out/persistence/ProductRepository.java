package org.onlinestore.application.port.out.persistence;

import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findByNameOrDescription(String query);
    void save(Product product);
    Optional<Product> findById(ProductId productId);
}
