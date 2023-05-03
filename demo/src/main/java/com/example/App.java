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
        Set<Product> productSet = new HashSet<>(); // productSet store all product objects
        List<ShoppingCart> cartList = new ArrayList<>(); // cartList store all cart objects

        // get data from files and create product objects and cart objects
        try {
            // get physical products data using streams and store all of them in physicalSet
            Set<Product> physicalSet = Files.lines(Paths.get("demo/src/data/products.txt"))
            .skip(5) // skip 5 lines of example in products.txt
            .filter(line -> line.contains("PHYSICAL")) // get only line with product type PHYSICAL
            .map(line -> {
                String[] productData = line.split(","); // split String to array

                // Create product objects with each line of data in file
                PhysicalProduct physicalProduct = new PhysicalProduct(productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3]),Double.parseDouble(productData[4]), productData[6], Boolean.valueOf(productData[5]));
                
                // set coupon String/code and coupon value
                    Double priceValue = Double.parseDouble(productData[7]);
                    String codePrice = productData[9];
                    physicalProduct.setCouponPrice(priceValue, codePrice);

                    int percentValue = Integer.parseInt(productData[8]);
                    String codePercent = productData[10];
                    physicalProduct.setPercentCoupon(percentValue, codePercent);

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
                
                     // set coupon String/code and coupon value
                    Double priceValue = Double.parseDouble(productData[7]);
                    String codePrice = productData[9];
                    digitalProduct.setCouponPrice(priceValue, codePrice);

                    Integer percentValue = Integer.parseInt(productData[8]);
                    String codePercent = productData[10];
                    digitalProduct.setPercentCoupon(percentValue, codePercent);

                return digitalProduct; // return all digital objects to digitalSet
            })
            .collect(Collectors.toSet());

            // Store all product objects from digitalSet and physicalSert in a productSet
            productSet.addAll(physicalSet);
            productSet.addAll(digitalSet);

            cartList = Files.lines(Paths.get("demo/src/data/carts.txt"))
            .skip(4) // skip 4 lines of example in carts.txt
            .map (line -> {
                String[] dataLine = line.split("; "); // split data of a cart to many array
                ShoppingCart shoppingCart = new ShoppingCart(); // create cart objects

                // split each dataLine into smaller array and add them to cart
                for (String strings : dataLine) {
                    String[] cart = strings.split(",");
                    shoppingCart.setData(cart); // add each smaller array to the cart
                }

                return shoppingCart; // return all cart objects to cartList
            })
            .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
           // Select cart
           Scanner sc = new Scanner(System.in);
           String cartNumber = "";
           int size = cartList.size();

           try {
            System.out.println("There are: "+ size +" carts, please chose cart number between 1 - " +size);
            System.out.println("Enter cart number OR enter QUIT/quit to exit program.");
            cartNumber = sc.nextLine();
            if (cartNumber.equals("QUIT") || cartNumber.equals("quit")) { // If user enter quit/Quit then exit the program
                break; 
            }
            ShoppingCart theCart = cartList.get(Integer.parseInt(cartNumber)-1);
            theCart.setProductSet(productSet); // set productSet to cart
            theCart.showCartDetail(theCart);
            System.out.println("\n");

            boolean running = true;
            // UI using Switch case
            while (running) {
                theCart.setProductSet(productSet); // update new productSet data to cart
                try {
                    System.out.println("Enter an option to continue:");
                    System.out.println(
                                "1 Add new product to store\n" +
                                "2 Remove product from store\n" +
                                "3 Edit product\n" +
                                "4 Add product items to shopping carts\n" +
                                "5 Remove product items from shopping carts\n" +
                                "6 View messages for gift product items\n" +
                                "7 Update messages for gift product items\n" +
                                "8 Apply coupon to cart\n" +
                                "9 Remove coupons to cart\n" +
                                "10 View cart details\n" +
                                "11 Print purchase receipts\n" +
                                "12 Sort cart\n" +
                                "0 Back to select cart\n" +
                                "Please enter an option:"
                                );
                    int option = sc.nextInt();
                    sc.nextLine(); // take the next line
                    switch (option) {
                        case 1: {
                            // Add new product to store
                            break;
                        }
                        case 2: {
                            // Remove product from store
                            break;
                        }
                        case 3: {
                            // Edit product
                            break;
                        }
                        case 4: {
                            // Add product items to shopping carts
                            break;
                        }
                        case 5: {
                            // Remove product items from shopping carts
                            break;
                        }
                        case 6: {
                            // View messages for gift product items
                            theCart.getMessage();
                            System.out.println("\n");
                            break;
                        }
                        case 7: {
                            // Update messages for gift product items
                            theCart.setMessage();
                            System.out.println("\n");
                            break;
                        }
                        case 8: {
                            // Apply coupon to cart
                            theCart.addCoupon();
                            System.out.println("\n");
                            break;
                        }
                        case 9: {
                            // Remove coupons to cart
                            theCart.removeCoupon();
                            System.out.println("\n");
                            break;
                        }
                        case 10: {
                            // View cart details
                            theCart.showCartDetail(theCart);
                            System.out.println("\n");
                            break;
                        }
                        case 11: {
                            // Print purchase receipts
                            break;
                        }
                        case 12: {
                            // Sort cart
                            break;
                        }
                        case 0: {
                            // Go back to select cart
                            System.out.println("BACK");
                            running = false;
                        }
                        default: {
                            System.out.println("Invalid option!");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid option!");
                }
            }
            System.out.println("Back to selecting cart!\n");
           } catch (Exception e) {
            System.out.println("Invalid cart number!");
           }
        }
    }
}
