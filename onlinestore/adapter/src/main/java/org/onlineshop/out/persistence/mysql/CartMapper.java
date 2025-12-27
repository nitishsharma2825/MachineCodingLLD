package org.onlineshop.out.persistence.mysql;

import org.onlinestore.model.cart.Cart;
import org.onlinestore.model.cart.CartLineItem;
import org.onlinestore.model.customer.CustomerId;

import java.util.Optional;

final class CartMapper {
    private CartMapper() {}

    static CartMySQLEntity toMySQLEntity(Cart cart) {
        CartMySQLEntity entity = new CartMySQLEntity();
        entity.setCustomerId(cart.id().value());

        entity.setLineItems(
                cart.lineItems().stream().map(lineItem -> toMySQLEntity(entity, lineItem)).toList()
        );

        return entity;
    }

    static CartLineItemMySQLEntity toMySQLEntity(
            CartMySQLEntity cartMySQLEntity, CartLineItem lineItem) {
        ProductMySQLEntity productMySQLEntity = new ProductMySQLEntity();
        productMySQLEntity.setId(lineItem.product().id().value());

        CartLineItemMySQLEntity entity = new CartLineItemMySQLEntity();
        entity.setCart(cartMySQLEntity);
        entity.setProduct(productMySQLEntity);
        entity.setQuantity(lineItem.quantity());
        return entity;
    }

    static Optional<Cart> toModelEntityOptional(CartMySQLEntity cartMySQLEntity) {
        if (cartMySQLEntity == null) return Optional.empty();

        CustomerId customerId = new CustomerId(cartMySQLEntity.getCustomerId());
        Cart cart = new Cart(customerId);

        for (CartLineItemMySQLEntity lineItemMySQLEntity: cartMySQLEntity.getLineItems()) {
            cart.putProductIgnoringNotEnoughItemsInStock(
                    ProductMapper.toModelEntity(lineItemMySQLEntity.getProduct()),
                    lineItemMySQLEntity.getQuantity()
            );
        }

        return Optional.of(cart);
    }
}
