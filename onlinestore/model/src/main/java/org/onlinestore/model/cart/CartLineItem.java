package org.onlinestore.model.cart;

import org.onlinestore.model.money.Money;
import org.onlinestore.model.product.Product;

import java.util.Objects;

public class CartLineItem {
    private final Product product;
    private int quantity;

    public CartLineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product product() {
        return product;
    }

    public int quantity() {
        return quantity;
    }

    // business logic implemented within the model class
    // Rich domain model vs Anemic domain model
    public void increaseQuantityBy(int augend, int itemsInStock) throws NotEnoughItemsInStockException {
        if (augend < 1) {
            throw new IllegalArgumentException("You must add at least one item");
        }

        int newQuantity = this.quantity + augend;
        if (itemsInStock < newQuantity) {
            throw new NotEnoughItemsInStockException(
                    ("Product %s has less items in stock (%d) than" +
                            "the requested total quantity (%d)")
                            .formatted(this.product.id(), this.product.itemsInStock(), newQuantity),
                    product.itemsInStock()
            );
        }

        this.quantity = newQuantity;
    }

    public Money subTotal() {
        return this.product.price().multiply(quantity);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CartLineItem that = (CartLineItem) object;
        return quantity == that.quantity && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public String toString() {
        return "CartLineItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
