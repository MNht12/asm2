package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ShoppingCart implements isGift{
    private ArrayList<String[]> cart;
    private Set<Product> productSet;

    private String message;

    public ShoppingCart(Set<Product> productSet) {
        this.productSet = productSet;
        this.cart = new ArrayList<String[]>();
    }

    public void test() {
        int n = 0;
        for (Product product : productSet) {
            n += 1;
            System.out.println(product +", number: "+ n);
        }
    }

    public void setData(String[] data) {
        cart.add(data);
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

    @Override
    public String toString() {
        return "ShoppingCart [cart=" + cart + "]";
    }

    public ArrayList<String[]> getCart() {
        return cart;
    }

    public void setCart(ArrayList<String[]> cart) {
        this.cart = cart;
    }

    
}
