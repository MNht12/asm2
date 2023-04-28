package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class ShoppingCart implements isGift{
    private String[] cart;
    private Set<Product> productSet;

    private String message;

    public ShoppingCart(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public String[] getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "ShoppingCart [cart=" + Arrays.toString(cart) + ", productSet=" + productSet + ", message=" + message
                + "]";
    }

    public void setCart(String[] cart) {
        this.cart = cart;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    // Override isGift interface methods
    @Override
    public void setMessage(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
