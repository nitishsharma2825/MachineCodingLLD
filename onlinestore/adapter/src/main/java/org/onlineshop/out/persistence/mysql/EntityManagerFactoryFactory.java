package org.onlineshop.out.persistence.mysql;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

public final class EntityManagerFactoryFactory {
    private EntityManagerFactoryFactory() {}

    public static EntityManagerFactory createMySQLEntityManagerFactory(
            String jdbcUrl, String user, String password) {
        return Persistence.createEntityManagerFactory(
                "org.onlinestore.adapter.out.persistence.mysql",
                Map.of(
                        "hibernate.dialect",                 "org.hibernate.dialect.MySQLDialect",
                        "hibernate.hbm2ddl.auto",            "update",
                        "jakarta.persistence.jdbc.driver",   "com.mysql.jdbc.Driver",
                        "jakarta.persistence.jdbc.url",      jdbcUrl,
                        "jakarta.persistence.jdbc.user",     user,
                        "jakarta.persistence.jdbc.password", password));
    }
}
