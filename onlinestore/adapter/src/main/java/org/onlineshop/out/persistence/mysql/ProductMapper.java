package org.onlineshop.out.persistence.mysql;

import org.onlinestore.model.money.Money;
import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductId;

import java.util.Currency;
import java.util.List;
import java.util.Optional;

final class ProductMapper {
    private ProductMapper() {}

    static ProductMySQLEntity toMySQLEntity(Product product) {
        ProductMySQLEntity entity = new ProductMySQLEntity();
        entity.setId(product.id().value());
        entity.setName(product.name());
        entity.setDescription(product.description());
        entity.setPriceCurrency(product.price().currency().getCurrencyCode());
        entity.setPriceAmount(product.price().amount());
        entity.setItemsInStock(product.itemsInStock());

        return entity;
    }

    static Optional<Product> toModelEntityOptional(ProductMySQLEntity mySQLEntity) {
        return Optional.ofNullable(mySQLEntity).map(ProductMapper::toModelEntity);
    }

    static Product toModelEntity(ProductMySQLEntity mySQLEntity) {
        return new Product(
                new ProductId(mySQLEntity.getId()),
                mySQLEntity.getName(),
                mySQLEntity.getDescription(),
                new Money(
                        Currency.getInstance(mySQLEntity.getPriceCurrency()),
                        mySQLEntity.getPriceAmount()
                ),
                mySQLEntity.getItemsInStock()
        );
    }

    static List<Product> toModelEntities(List<ProductMySQLEntity> mysqlEntities) {
        return mysqlEntities.stream().map(ProductMapper::toModelEntity).toList();
    }
}
