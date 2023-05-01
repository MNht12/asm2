package com.example;

import java.io.*;
import java.util.stream.*;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            // get physical products data using streams and store all of them in physicalSet
            Set<Product> physicalSet = Files.lines(Paths.get("demo/src/data/products.txt"))
            .skip(5) // skip 5 lines of example in products.txt
            .filter(line -> line.contains("PHYSICAL")) // get only line with product type PHYSICAL
            .map(line -> {
                String[] productData = line.split(","); // split String to array

                // Create product objects with each line of data in file
                PhysicalProduct physicalProduct = new PhysicalProduct(productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3]),Double.parseDouble(productData[4]), productData[6], Boolean.valueOf(productData[5]));
                
                // Check productData if price coupon and percent coupon are not null, if not then set value to isCoupon interface
                if (!productData[7].equals("null")) {
                    physicalProduct.setCouponPrice(Double.parseDouble(productData[7]));
                }
                if (!productData[8].equals(0)) {
                    physicalProduct.setPercentCoupon(Integer.parseInt(productData[8]));
                }

                return physicalProduct; // return all physical objects to physicalSet
            })
            .collect(Collectors.toSet());

            // get digital products data using streams and store all of them in digitalSet
            Set<Product> digitalSet = Files.lines(Paths.get("demo/src/data/products.txt"))
            .skip(5) // skip 5 lines of example in products.txt
            .filter(line -> line.contains("DIGITAL")) // get only line with product type DIGITAL
            .map(line -> {
                String[] productData = line.split(","); // split String to array

                // Create product objects with each line of data in file
                DigitalProduct digitalProduct = new DigitalProduct(productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3]), productData[6], Boolean.valueOf(productData[5]));
                
                // Check productData if price coupon and percent coupon are not null/0, if not then set value to isCoupon interface
                if (!productData[7].equals("null")) {
                    digitalProduct.setCouponPrice(Double.parseDouble(productData[7]));
                }
                if (!productData[8].equals(0)) {
                    digitalProduct.setPercentCoupon(Integer.parseInt(productData[8]));
                }

                return digitalProduct; // return all digital objects to digitalSet
            })
            .collect(Collectors.toSet());

            // Store all product objects from digitalSet and physicalSert in a productSet
            Set<Product> productSet = new HashSet<>(physicalSet);
            productSet.addAll(digitalSet);

            List<ShoppingCart> cartList = Files.lines(Paths.get("demo/src/data/carts.txt"))
            .skip(4) // skip 4 lines of example in carts.txt
            .map (line -> {
                String[] dataLine = line.split("; "); // split data of a cart to many array
                ShoppingCart shoppingCart = new ShoppingCart(productSet); // create cart objects

                // split each dataLine into smaller array and add them to cart
                for (String strings : dataLine) {
                    String[] cart = strings.split(",");
                    shoppingCart.setData(cart);
                }

                return shoppingCart; // return all cart objects to cartList
            })
            .collect(Collectors.toList());
            
            // Select cart
            int cartNumber = 0;
            int size = cartList.size();
            System.out.println("There are: "+ size +" carts, please chose cart number between 1 - " +size);
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter cart number:");
            cartNumber = 1 - Integer.parseInt(sc.nextLine());
            ShoppingCart cart = cartList.get(cartNumber);

            ArrayList<String[]> cartProducts = cart.getCart(); // Get all product info from cart
            cart.setMessage();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
