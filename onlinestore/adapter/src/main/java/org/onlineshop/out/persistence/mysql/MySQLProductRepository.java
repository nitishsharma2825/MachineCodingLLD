package org.onlineshop.out.persistence.mysql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.onlineshop.out.persistence.DemoProducts;
import org.onlinestore.application.port.out.persistence.ProductRepository;
import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductId;

import java.util.List;
import java.util.Optional;

public class MySQLProductRepository implements ProductRepository {
    private final EntityManagerFactory entityManagerFactory;

    public MySQLProductRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        createDemoProducts();
    }

    private void createDemoProducts() {
        DemoProducts.DEMO_PRODUCTS.forEach(this::save);
    }

    @Override
    public List<Product> findByNameOrDescription(String queryString) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<ProductMySQLEntity> query = entityManager.createQuery(
                    "from ProductMySQLEntity where name like :query or description like :query",
                    ProductMySQLEntity.class
            ).setParameter("query", "%" + queryString + "%");

            List<ProductMySQLEntity> entities = query.getResultList();

            return ProductMapper.toModelEntities(entities);
        }
    }

    @Override
    public void save(Product product) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(ProductMapper.toMySQLEntity(product));
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            ProductMySQLEntity mySQLEntity = entityManager.find(ProductMySQLEntity.class, productId.value());
            return ProductMapper.toModelEntityOptional(mySQLEntity);
        }
    }
}
