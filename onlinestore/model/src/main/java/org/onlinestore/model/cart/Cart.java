package org.onlinestore.model.cart;

import org.onlinestore.model.customer.CustomerId;
import org.onlinestore.model.money.Money;
import org.onlinestore.model.product.Product;
import org.onlinestore.model.product.ProductId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private final CustomerId id;
    private final Map<ProductId, CartLineItem> lineItems = new HashMap<>();
    public Cart(CustomerId id) {
        this.id = id;
    }

    public CustomerId id() {
        return id;
    }

    public void addProduct(Product product, int quantity) throws NotEnoughItemsInStockException {
        lineItems
                .computeIfAbsent(product.id(), ignored -> new CartLineItem(product, 0))
                .increaseQuantityBy(quantity, product.itemsInStock());
    }

    public void putProductIgnoringNotEnoughItemsInStock(Product product, int quantity) {
        lineItems.put(product.id(), new CartLineItem(product, quantity));
    }

    public List<CartLineItem> lineItems() {
        return List.copyOf(lineItems.values());
    }

    public int numberOfItems() {
        return lineItems.values().stream().mapToInt(CartLineItem::quantity).sum();
    }

    public Money subTotal() {
        return lineItems.values().stream()
                .map(CartLineItem::subTotal)
                .reduce(Money::add)
                .orElse(null);
    }
}
