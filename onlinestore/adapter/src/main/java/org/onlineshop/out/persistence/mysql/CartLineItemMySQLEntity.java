package org.onlineshop.out.persistence.mysql;

import jakarta.persistence.*;

@Entity
@Table(name = "CartLineItem")
public class CartLineItemMySQLEntity {
    @Id @GeneratedValue private Integer id;

    // many carts can have this item
    // creates a column cart_id in the database table and a foreign key relationship with Cart table
    @ManyToOne private CartMySQLEntity cart;
    @ManyToOne private ProductMySQLEntity product;
    private int quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CartMySQLEntity getCart() {
        return cart;
    }

    public void setCart(CartMySQLEntity cart) {
        this.cart = cart;
    }

    public ProductMySQLEntity getProduct() {
        return product;
    }

    public void setProduct(ProductMySQLEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
