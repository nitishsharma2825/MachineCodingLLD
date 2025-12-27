package org.onlineshop.out.persistence.mysql;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Cart")
public class CartMySQLEntity {
    @Id private int customerId;

    // causes to read all entries from CartLineItem table that references this cart and store as list
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartLineItemMySQLEntity> lineItems;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<CartLineItemMySQLEntity> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<CartLineItemMySQLEntity> lineItems) {
        this.lineItems = lineItems;
    }
}
