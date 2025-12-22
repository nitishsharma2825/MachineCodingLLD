package org.onlineshop.in.rest.cart;

import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.money.Money;

import java.util.List;

public record CartWebModel(List<CartLineItemWebModel> lineItems, int numberOfItems, Money subTotal) {
    public static CartWebModel fromDomainModel(Cart cart) {
        return new CartWebModel(
                cart.lineItems().stream().map(CartLineItemWebModel::fromDomainModel).toList(),
                cart.numberOfItems(),
                cart.subTotal()
        );
    }
}
