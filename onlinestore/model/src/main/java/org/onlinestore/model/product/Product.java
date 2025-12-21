package org.onlinestore.model.product;

import org.onlinestore.model.money.Money;

import java.util.Objects;

public class Product {
    private final ProductId id;
    private String name;
    private String description;
    private Money price;
    private int itemsInStock;

    public Product(ProductId id, String name, String description, Money price, int itemsInStock) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = name;
        this.description = description;
        this.price = price;
        this.itemsInStock = itemsInStock;
    }

    public ProductId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Money price() {
        return price;
    }

    public int itemsInStock() {
        return itemsInStock;
    }

    // Setters
    public Product name(String name) {
        this.name = name;
        return this;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public Product price(Money price) {
        this.price = price;
        return this;
    }

    public Product itemsInStock(int itemsInStock) {
        this.itemsInStock = itemsInStock;
        return this;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Product product = (Product) object;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", itemsInStock=" + itemsInStock +
                '}';
    }
}
