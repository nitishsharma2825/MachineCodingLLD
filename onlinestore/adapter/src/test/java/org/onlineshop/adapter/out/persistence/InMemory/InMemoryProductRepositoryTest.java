package org.onlineshop.adapter.out.persistence.InMemory;

import org.onlineshop.adapter.out.persistence.AbstractProductRepositoryTest;
import org.onlineshop.out.persistence.inmemory.InMemoryProductRepository;

public class InMemoryProductRepositoryTest extends AbstractProductRepositoryTest<InMemoryProductRepository> {
    @Override
    protected InMemoryProductRepository createProductRepository() {
        return new InMemoryProductRepository();
    }
}
