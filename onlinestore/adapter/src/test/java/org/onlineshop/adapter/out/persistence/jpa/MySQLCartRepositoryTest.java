package org.onlineshop.adapter.out.persistence.jpa;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.onlineshop.adapter.out.persistence.AbstractCartRepositoryTest;
import org.onlineshop.out.persistence.mysql.EntityManagerFactoryFactory;
import org.onlineshop.out.persistence.mysql.MySQLCartRepository;
import org.onlineshop.out.persistence.mysql.MySQLProductRepository;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class MySQLCartRepositoryTest extends AbstractCartRepositoryTest<MySQLCartRepository, MySQLProductRepository> {
    private static MySQLContainer<?> mysql;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void startDatabase() {
        mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"));
        mysql.start();

        entityManagerFactory =
                EntityManagerFactoryFactory.createMySQLEntityManagerFactory(
                        mysql.getJdbcUrl(), "root", "test");
    }

    @AfterAll
    static void stopDatabase() {
        entityManagerFactory.close();
        mysql.stop();
    }

    @Override
    protected MySQLCartRepository createCartRepository() {
        return new MySQLCartRepository(entityManagerFactory);
    }

    @Override
    protected MySQLProductRepository createProductRepository() {
        return new MySQLProductRepository(entityManagerFactory);
    }
}
