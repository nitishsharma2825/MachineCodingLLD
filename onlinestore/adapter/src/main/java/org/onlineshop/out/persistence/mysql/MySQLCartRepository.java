package org.onlineshop.out.persistence.mysql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.onlinestore.application.port.out.persistence.CartRepository;
import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.customer.CustomerId;

import java.util.Optional;

public class MySQLCartRepository implements CartRepository {
    private final EntityManagerFactory entityManagerFactory;

    public MySQLCartRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<Cart> findByCustomerId(CustomerId customerId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CartMySQLEntity mySQLEntity = entityManager.find(CartMySQLEntity.class, customerId.value());
            return CartMapper.toModelEntityOptional(mySQLEntity);
        }
    }

    @Override
    public void save(Cart cart) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(CartMapper.toMySQLEntity(cart));
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(CustomerId customerId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            CartMySQLEntity cartMySQLEntity =
                    entityManager.find(CartMySQLEntity.class, customerId.value());

            if (cartMySQLEntity != null) {
                entityManager.remove(cartMySQLEntity);
            }

            entityManager.getTransaction().commit();
        }
    }
}
