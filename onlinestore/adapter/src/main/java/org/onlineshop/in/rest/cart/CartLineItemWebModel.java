package org.onlineshop.in.rest.cart;

import org.onlinestore.model.cart.CartLineItem;
import org.onlinestore.model.money.Money;
import org.onlinestore.model.product.Product;

public record CartLineItemWebModel(String productId, String productName, Money price, int quantity) {
    public static CartLineItemWebModel fromDomainModel(CartLineItem lineItem) {
        Product product = lineItem.product();
        return new CartLineItemWebModel(
                product.id().value(), product.name(), product.price(), lineItem.quantity()
        );
    }
}
