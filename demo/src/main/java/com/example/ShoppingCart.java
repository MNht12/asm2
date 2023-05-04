package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ShoppingCart implements isGift{
    private ArrayList<String[]> cart;
    private Set<Product> productSet;

    public ShoppingCart() {
        cart = new ArrayList<String[]>();
    }

    // Show cart details
    public void showCartDetail(ShoppingCart theCart) {
        // theCart.setProductSet(productSet); //set productSet to cart
        ArrayList<String[]> cart = theCart.getCart();

        String[] cartcoupon = cart.get(0);
        String coupon = cartcoupon[0];
        if (cartcoupon[0].equals("null")) {
            coupon = "<No coupon>";
        }
        System.out.println("Cart coupon: "+coupon);
        System.out.println("All items in this cart:");
        int cartSize = cart.size();
        List<String> allCoupon = new ArrayList<>();
        for (int i = 1; i < cartSize; i++) {
            String[] productItem = cart.get(i);
            String itemName = productItem[0];
            String itemQuantity = productItem[1];
            String itemGiftMessage = productItem[2];
            if (itemGiftMessage.equals("null")) {
                itemGiftMessage = "<No gift message>";
            }
            System.out.println("Item name: " +itemName+", Item quantity: "+itemQuantity+", Item gift message: "+itemGiftMessage);
            for (Product product : productSet) {
                if (product.getName().equals(itemName)) {
                    if (!product.getPriceCode().equals("null")) {
                        allCoupon.add(product.getPriceCode());
                    }
                    if (!product.getPercentCode().equals("null")) {
                        allCoupon.add(product.getPercentCode());
                    }
                }
            }
        }
        System.out.println("Here are all the coupons that you can apply to this cart: "+allCoupon);
    }

    // add/change coupon method
    public void addCoupon() {
        Scanner sc = new Scanner(System.in);
        System.out.println("By entering a coupon, the previous coupon (if have) will be replaced with the new coupon!");
        while (true) {
            Boolean foundCoupon = false;
            System.out.println("Enter coupon code OR enter QUIT/quit to go back:");
            String code = sc.nextLine();
            if (code.equals("QUIT") || code.equals("quit")) { // If user enter quit then break/go back
                break;
            }
            for (Product product : productSet) {
                // check product price coupon
                if (product.getPriceCode().equals(code)) { 
                    cart.get(0)[0] = code; // change old coupon to mew coupon
                    foundCoupon = true;
                    break;
                }

                // check product percent coupon
                if (product.getPercentCode().equals(code)) { 
                    cart.get(0)[0] = code; // change old coupon to new coupon
                    foundCoupon = true;
                    break;
                }
            }
            if (!foundCoupon) {
                System.out.println("There is no coupon code: " + code + ", or this code cannot apply to this cart.");
            } else {
                System.out.println("Coupon for this cart has been changed to: " + code);
                break;
            }
        }
    }

    // remove coupon method
    public void removeCoupon() {
        String noCode = "null";
        cart.get(0)[0] = noCode; // remove coupon and change to "null"
        System.out.println("\nCoupon has been removed for this cart!\n");
    }

    public void setData(String[] data) {
        cart.add(data);
    }

    public Set<Product> getProductSet(Set<Product> productSet) {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }


    // Override isGift interface methods
    @Override
    public void setMessage() {
        // Print out all product name in the cart
        System.out.println("All product items in this cart: ");
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
                productItem = product; // store all product in productItem
            }
        }
        if (!productFound) {
            System.out.println("There is no " +productName+" item in this cart"); // If user enter an incorrect name
        }

        if (productFound) {
            // Get product object info
            for (Product product : productSet) {
                if (product.getName().equals(productName)) { // check name of that item with product name
                    if (product.getisGift()) { // check if product is a gift product or not
                        System.out.println("This product can be a gift product. Please enter a Gift Message:");
                        String giftMessage = sc.nextLine();
                        System.out.println("");
                        System.out.println("Gift message for Product Item: "+productName+" in this cart has been updated to: "+giftMessage); 
                        
                        // check for item name and change gift message
                        for (int i = 0; i < cart.size(); i++) {
                            if (cart.get(i)[0].equals(productName)) {
                                cart.get(i)[2] = giftMessage;
                            }
                        }
                    } else {
                        System.out.println("This " +product+ " cannot be a gift product!"); // If product item is not a gift product
                    }
                }
            }
        }
    }

    @Override
    public void getMessage() {
        System.out.println("All product items in this cart: ");
        for (int i = 1; i < cart.size(); i++) {
            String[] itemData = cart.get(i);
            System.out.println("Name: "+itemData[0]);
        }
        System.out.println("Enter Product name: ");
        Scanner sc = new Scanner(System.in);
        String productName = sc.nextLine();

        // Check if user enter a correct product item name and if yes, store that item in productItem
        boolean productFound = false;
        for (String[] product : cart) {
            if (product[0].equals(productName)) {
                String message = product[2];
                if (message.equals("null")) {
                    message = "Product item: " +productName+" does not have a gift message!";
                }
                System.out.println("Product item: " +productName+"'s gift message: "+message);
                productFound = true;
            }
        }
        if (!productFound) {
            System.out.println("There is no " +productName+" item in this cart"); // If user enter an incorrect name
        }
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
