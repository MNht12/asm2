package com.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;

public class ShoppingList {
  LinkedList<Product> productSet;

  // public ShoppingList(Set<Product> productSet) {
  // this.productSet = productSet;
  // }

  public ShoppingList() {
    productSet = new LinkedList<Product>();
  }

  public List<Product> getProducts() {
    return productSet;
  }

  public void addProductD(String name, String description, int quantity, double price, String tax, Boolean isGift) {
    productSet.add(new DigitalProduct(name, description, quantity, price, tax, isGift));
  }

  public void addProductP(String name, String description, int quantity, double price, double weight, String tax,
      boolean isGift) {
    productSet.add(new PhysicalProduct(name, description, quantity, price, weight, tax, isGift));
  }

}
