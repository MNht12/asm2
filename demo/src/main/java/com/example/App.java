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
        
        try {
            Set<Product> physicalSet = Files.lines(Paths.get("demo/src/data/products.txt"))
            .skip(2)
            .filter(line -> line.contains("PHYSICAL"))
            .map(line -> {
                String[] productData = line.split(",");
                return new PhysicalProduct(productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3]), Double.parseDouble(productData[4])); 
            })
            .collect(Collectors.toSet());

            Set<Product> digitalSet = Files.lines(Paths.get("demo/src/data/products.txt"))
            .skip(2)
            .filter(line -> line.contains("DIGITAL"))
            .map(line -> {
                String[] productData = line.split(",");
                return new DigitalProduct(productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3])); 
            })
            .collect(Collectors.toSet());

            Set<Product> productSet = new HashSet<>(physicalSet);
            productSet.addAll(digitalSet);

            System.out.println(productSet);

            for (Product product : productSet) {
                System.out.println(product);
            }
        } catch (IOException e) {
            System.out.println("Error in main");
        }

    }
}
