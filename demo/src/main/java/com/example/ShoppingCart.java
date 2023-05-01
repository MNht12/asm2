package com.example;

import java.util.ArrayList;
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
    public void setMessage() {
        // Print out all product name in the cart
        System.out.println("All product item in this cart: ");
        for (int i = 1; i < cart.size(); i++) {
            String[] itemData = cart.get(i);
            System.out.println("Name: "+itemData[0]);
        }

        System.out.println("Enter Product name: ");
        Scanner sc = new Scanner(System.in);
        String productName = sc.nextLine();

        // Check if user enter a correct product item name and if yes, store that item in productItem
        String[] productItem = new String[0];
        boolean productFound = false;
        for (String[] product : cart) {
            if (product[0].equals(productName)) {
                productFound = true;
                productItem = product;
            }
        }
        if (!productFound) {
            System.out.println("There is no " +productName+" item in this cart"); // If user enter an incorrect name
        }

        if (productFound) {
            // Get product object info
            for (Product product : productSet) {
                if (product.getName().equals(productName)) { // check name and get information of that product item
                    if (product.getisGift()) { // check if product is a gift product or not
                        System.out.println("This product can be a gift product. Please enter a Gift Message:");
                        String giftMessage = sc.nextLine();
                        String itemName = productItem[0];
                        String itemMessage = productItem[2];
                        itemMessage = giftMessage;
                        System.out.println("");
                        System.out.println("Gift message for Product Item: "+itemName+" in this cart has been updated to: "+itemMessage); 
                        message = itemMessage;
                    } else {
                        System.out.println("This " +product+ " cannot be a gift product!"); // If product item is not a gift product
                    }
                }
            }
        }
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
