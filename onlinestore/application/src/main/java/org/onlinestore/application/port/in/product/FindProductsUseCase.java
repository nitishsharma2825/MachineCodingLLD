package org.onlinestore.application.port.in.product;

import org.onlinestore.model.product.Product;

import java.util.List;

public interface FindProductsUseCase {
    List<Product> findByNameOrDescription(String query);
}
