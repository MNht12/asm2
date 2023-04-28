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
        // get products data using streams
        try {
            Set<Product> physicalSet = Files.lines(Paths.get("demo/src/data/products.txt"))
            .skip(3)
            .filter(line -> line.contains("PHYSICAL"))
            .map(line -> {
                String[] productData = line.split(",");
                PhysicalProduct physicalProduct = new PhysicalProduct(productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3]),Double.parseDouble(productData[4]), productData[6]);
                
                // Check productData if price coupon and percent coupon are not null, if not then set value to isCoupon interface
                if (!productData[7].equals("null")) {
                    physicalProduct.setCouponPrice(Double.parseDouble(productData[7]));
                }
                if (!productData[8].equals(0)) {
                    physicalProduct.setPercentCoupon(Integer.parseInt(productData[8]));
                }

                return physicalProduct;
            })
            .collect(Collectors.toSet());

            Set<Product> digitalSet = Files.lines(Paths.get("demo/src/data/products.txt"))
            .skip(3)
            .filter(line -> line.contains("DIGITAL"))
            .map(line -> {
                String[] productData = line.split(",");
                DigitalProduct digitalProduct = new DigitalProduct(productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3]), productData[6]);
                
                // Check productData if price coupon and percent coupon are not null, if not then set value to isCoupon interface
                if (!productData[7].equals("null")) {
                    digitalProduct.setCouponPrice(Double.parseDouble(productData[7]));
                }
                if (!productData[8].equals(0)) {
                    digitalProduct.setPercentCoupon(Integer.parseInt(productData[8]));
                }

                return digitalProduct;
            })
            .collect(Collectors.toSet());

            // Store all products in a productSet
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
            .skip(4)
            .map (line -> {
                String[] cartLine = line.split("; ");
                // System.out.println(Arrays.toString(cartLine));
                ShoppingCart shoppingCart = new ShoppingCart(productSet);
                // shoppingCart.setData(cartLine[0].split(","));
                for (String strings : cartLine) {
                    String[] cart = strings.split(",");
                    shoppingCart.setData(cart);
                }
                // shoppingCart.getCart();
                // System.out.println("\nAll product: ");
                // shoppingCart.test();
                return shoppingCart;
            })
            .collect(Collectors.toList());

            int n = 1;
            for (ShoppingCart cart : cartList) {
                System.out.println("Cart" +" "+ n);
                cart.getCart();
                n++;
                System.out.println("");
            }
            System.out.println("All products: ");
            cartList.get(1).test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
