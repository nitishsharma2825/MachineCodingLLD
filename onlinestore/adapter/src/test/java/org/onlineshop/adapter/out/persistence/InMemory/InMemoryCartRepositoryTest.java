package org.onlineshop.adapter.out.persistence.InMemory;

import org.onlineshop.adapter.out.persistence.AbstractCartRepositoryTest;
import org.onlineshop.out.persistence.inmemory.InMemoryCartRepository;
import org.onlineshop.out.persistence.inmemory.InMemoryProductRepository;

public class InMemoryCartRepositoryTest extends AbstractCartRepositoryTest<InMemoryCartRepository, InMemoryProductRepository> {
    @Override
    protected InMemoryCartRepository createCartRepository() {
        return new InMemoryCartRepository();
    }

    @Override
    protected InMemoryProductRepository createProductRepository() {
        return new InMemoryProductRepository();
    }
}
