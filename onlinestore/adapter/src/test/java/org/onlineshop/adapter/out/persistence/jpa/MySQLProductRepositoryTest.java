package org.onlineshop.adapter.out.persistence.jpa;

import jakarta.persistence.EntityManagerFactory;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.onlineshop.adapter.out.persistence.AbstractProductRepositoryTest;
import org.onlineshop.out.persistence.mysql.EntityManagerFactoryFactory;
import org.onlineshop.out.persistence.mysql.MySQLProductRepository;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class MySQLProductRepositoryTest extends AbstractProductRepositoryTest<MySQLProductRepository> {
    private static MySQLContainer<?> mysql;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void startDatabase() {
        mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.1"));
        mysql.start();

        entityManagerFactory = EntityManagerFactoryFactory.createMySQLEntityManagerFactory(mysql.getJdbcUrl(), "root", "test");
    }

    @AfterAll
    static void stopDatabase() {
        entityManagerFactory.close();
        mysql.stop();
    }

    @Override
    protected MySQLProductRepository createProductRepository() {
        return new MySQLProductRepository(entityManagerFactory);
    }
}
