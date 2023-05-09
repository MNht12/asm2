package com.example;

/**
 * @author Nguyen Minh Nhat, Nguyen Cong Thinh, Nguyen Dang Ha, Don Tuan Duong
 */

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
public class main {
    public static void main(String[] args) {
        Set<Product> productSet = new HashSet<>(); // productSet store all product objects
        List<ShoppingCart> cartList = new ArrayList<>(); // cartList store all cart objects

        ShoppingFeature feature = new ShoppingFeature();

        // get data from files and create product objects and cart objects
        try {
            // get physical products data using streams and store all of them in physicalSet
            Set<Product> physicalSet = Files.lines(Paths.get("demo/src/data/products.txt"))
                    .skip(5) // skip 5 lines of example in products.txt
                    .filter(line -> line.contains("PHYSICAL")) // get only line with product type PHYSICAL
                    .map(line -> {
                        String[] productData = line.split(","); // split String to array

                        // Create product objects with each line of data in file
                        PhysicalProduct physicalProduct = new PhysicalProduct(productData[0], productData[1],
                                Integer.parseInt(productData[2]), Double.parseDouble(productData[3]),
                                Double.parseDouble(productData[4]), productData[6], Boolean.valueOf(productData[5]));

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
                        DigitalProduct digitalProduct = new DigitalProduct(productData[0], productData[1],
                                Integer.parseInt(productData[2]), Double.parseDouble(productData[3]), productData[6],
                                Boolean.valueOf(productData[5]));

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

            // Create cart objects with streams and add data to cart
            cartList = Files.lines(Paths.get("demo/src/data/carts.txt"))
                    .skip(4) // skip 4 lines of example in carts.txt
                    .map(line -> {
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
            for (ShoppingCart shoppingCart : cartList) {
                shoppingCart.setProductSet(productSet);
            }
            feature.setProductSet(productSet); //set productSet to ShoppingFeature
            feature.sortCart(cartList); // sort all carts by weight

            // Select cart
            Scanner sc = new Scanner(System.in);
            String cartNumber = "";

            try {
                ShoppingCart theCart = cartList.get(0);
                boolean isOpen = false;
                while (!isOpen) {
                    int size = cartList.size();
                    System.out.println("There are: " + size + " carts, please chose cart number between 1 - " + size);
                    System.out.println("Enter cart number OR enter QUIT/quit to exit program.");
                    cartNumber = sc.nextLine();
                    if (cartNumber.equals("QUIT") || cartNumber.equals("quit")) { // If user enter quit/Quit then exit the program
                        isOpen = true;
                        break;
                    }
                    theCart = cartList.get(Integer.parseInt(cartNumber) - 1);
                    if (theCart.getStatus()) { // check cart status if close or not
                        theCart.showCartDetail(theCart);
                        System.out.println("\n");
                        isOpen = true;
                    } else {
                        System.out.println("This cart has been closed, please choose another cart!");
                    }
                }
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
                                        "9 Remove coupons from cart\n" +
                                        "10 View cart details\n" +
                                        "11 Print purchase receipts\n" +
                                        "12 Sort cart\n" +
                                        "13 Show all products in store\n" +
                                        "0 Back to select cart\n" +
                                        "Please enter an option:");
                        int option = sc.nextInt();
                        sc.nextLine(); // take the next line
                        switch (option) {
                            case 1: {
                                // Add new product to store
                                System.out.println("Enter 1 to add new digital product, 2 to add new physical product:");
                                String option2 = sc.nextLine();
                                if (option2.equals("1")) {
                                    productSet.add(ShoppingFeature.addDigital());
                                } else if (option2.equals("2")) {
                                    productSet.add(ShoppingFeature.addPhysical());
                                } else {
                                    System.out.println("Invalid input!");
                                }
                                System.out.println("\n");
                                break;
                            }
                            case 2: {
                                // Remove product
                                productSet = feature.removeProduct();
                                System.out.println("\n");
                                break;
                            }
                            case 3: {
                                // Edit product
                                productSet = feature.editProducts();
                                System.out.println("\n");
                                break;
                            }
                            case 4: {
                                // Add product items to shopping carts
                                System.out.println("Enter product name to add item to cart:");
                                String name = sc.nextLine();
                                System.out.println("Enter quantity:");
                                String quantity = sc.nextLine();
                                theCart.addItem(name,quantity);
                                System.out.println("\n");
                                break;
                            }
                            case 5: {
                                // Remove product items from shopping carts
                                theCart.removeItem(theCart);
                                System.out.println("\n");
                                break;
                            }
                            case 6: {
                                // View messages for gift product items
                                System.out.println("Enter Product name: ");
                                String productName = sc.nextLine();
                                theCart.getMessage(productName);
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
                                System.out.println("By entering a coupon, the previous coupon (if have) will be replaced with the new coupon!");
                                String code = sc.nextLine();
                                theCart.addCoupon(code);
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
                                theCart.cartAmount(theCart);
                                System.out.println("\n");
                                running = false;
                                break;
                            }
                            case 12: {
                                // Sort cart
                                int num = 0;
                                for (ShoppingCart shoppingCart : cartList) {
                                    num++;
                                    System.out.println("Cart No."+num+" Weight: "+shoppingCart.calculateWeight());
                                }
                                break;
                            }
                            case 13: {
                                // Show all products
                                for (Product p : productSet) {
                                    System.out.println(p.displayAll());
                                }
                                System.out.println("\n");
                                break;
                            }
                            case 0: {
                                // Go back to select cart
                                System.out.println("Going back");
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
