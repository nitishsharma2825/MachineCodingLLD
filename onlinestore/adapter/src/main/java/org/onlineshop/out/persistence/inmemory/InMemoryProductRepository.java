package org.onlineshop.out.persistence.inmemory;

import org.onlineshop.out.persistence.DemoProducts;
import org.onlinestore.application.port.out.persistence.ProductRepository;
import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductId;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<ProductId, Product> products = new ConcurrentHashMap<>();
    public InMemoryProductRepository() {
        createDemoProducts();
    }
    private void createDemoProducts() {
        DemoProducts.DEMO_PRODUCTS.forEach(this::save);
    }

    @Override
    public List<Product> findByNameOrDescription(String query) {
        String queryLowerCase = query.toLowerCase(Locale.ROOT);
        return products.values().stream().filter(product -> matchesQuery(product, queryLowerCase)).toList();
    }

    @Override
    public void save(Product product) {
        products.put(product.id(), product);
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return Optional.ofNullable(products.get(productId));
    }

    private boolean matchesQuery(Product product, String query) {
        return product.name().toLowerCase(Locale.ROOT).contains(query) ||
                    product.description().toLowerCase(Locale.ROOT).contains(query);
    }
}
