package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShoppingCart {
    private List<String> cart;
    private Set<Product> productSet;

    public ShoppingCart(Set<Product> productSet) {
        this.productSet = productSet;
        this.cart = new ArrayList<String>();
    }
}
