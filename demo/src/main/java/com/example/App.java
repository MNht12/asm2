package com.example;

import java.io.*;
import java.util.stream.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
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
                PhysicalProduct physicalProduct = new PhysicalProduct
                (
                    productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3]),
                    Double.parseDouble(productData[4]), productData[6]
                );
                physicalProduct.setCouponPrice(Double.parseDouble(productData[7]));
                physicalProduct.setPercentCoupon(Integer.parseInt(productData[8]));

                
                // Check productData if price coupon and percent coupon are not null, if not then set value to isCoupon interface
                
                if (productData[7] != null) {
                    physicalProduct.setCouponPrice(Double.parseDouble(productData[7]));
                }
                if (productData[8] != null) {
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
                DigitalProduct digitalProduct = new DigitalProduct
                (
                    productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3]), 
                    productData[6]
                );
                
                // Check productData if price coupon and percent coupon are not null, if not then set value to isCoupon interface
                if (productData[7] != null) {
                    digitalProduct.setCouponPrice(Double.parseDouble(productData[7]));
                }
                if (productData[8] != null) {
                    digitalProduct.setPercentCoupon(Integer.parseInt(productData[8]));
                }

                return digitalProduct;
            })
            .collect(Collectors.toSet());

            Set<Product> productSet = new HashSet<>(physicalSet);
            productSet.addAll(digitalSet);

            // Scanner sc = new Scanner(System.in);
            // System.out.println("enter product name:");
            // String name = sc.nextLine();
            // System.out.println("enter mess:");
            // String mess = sc.nextLine();

            for (Product product : productSet) {
                System.out.println(product);
                System.out.println(product.getCouponPrice());
                System.out.println(product.getPercentCoupon());

                // get set message UI
                // if (product.getName().equals(name)) {
                //     product.setMessage(mess);
                //     System.out.println(product.getName() + " " + product.getMessage());
                // }

                // get set message 
                // Scanner sc = new Scanner(System.in);
                // System.out.println("enter message:");
                // String mess = sc.nextLine();
                // product.setMessage(mess);
                // System.out.println(product.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // get carts data using streams
        try {
            
        } catch (Exception e) {
            System.out.println("Error in streams get data for carts");
        }
    }
}
