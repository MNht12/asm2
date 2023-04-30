package com.example;

import java.io.*;
import java.util.stream.*;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

            // Scanner sc = new Scanner(System.in);
            // System.out.println("enter product name:");
            // String name = sc.nextLine();
            // System.out.println("enter mess:");
            // String mess = sc.nextLine();

            // for (Product product : productSet) {
            //     System.out.println("\n");
            //     System.out.println(product.displayAll());

            //     // get set message UI
            //     if (product.getName().equals(name)) {
            //         product.setMessage(mess);
            //         System.out.println(product.getName() + " " + product.getMessage());
            //     }

            //     // get set message 
            //     Scanner sc = new Scanner(System.in);
            //     System.out.println("enter message:");
            //     String mess = sc.nextLine();
            //     product.setMessage(mess);
            //     System.out.println(product.getMessage());
            // }

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

            // Add item to cart example
            Scanner sc = new Scanner(System.in);
            System.out.println("enter cart number:");
            cartNumber = Integer.parseInt(sc.nextLine());
            String[] addPro = {"pen12", "12", "pen12 gift"}; // example data
            ShoppingCart cart = cartList.get(cartNumber);
            cart.setData(addPro);

            ArrayList<String[]> cartProducts = cart.getCart(); // Get all product info from cart

            int num = cartProducts.size(); // Check how many products
            for (int i = 1; i < num; i++) {

                // Get info/data of each product
                String[] productData = cartProducts.get(i);
                String name = productData[0];
                String quantity = productData[1];
                String message = "";
                if (productData[2].equals("null")) {
                    message = "No gift message!";
                } else {
                    message = productData[2]; 
                }
                System.out.println("Product name: "+ name + ", Quantity: " + quantity + ", Gift message: " + message);
                System.out.println(productData[0] = "Change Name"); 
                System.out.println("");
            }

            // System.out.println("All products: ");
            // n =1;
            // for (Product product : productSet) {
            //     System.out.println(product.displayAll() +" "+n);
            //     n++;
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
