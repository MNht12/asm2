package com.example;

import java.io.*;
import java.util.stream.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // try {
        //     BufferedReader br = new BufferedReader(new FileReader("data/products.txt"));
        //     String s;
        //     System.out.println("in");
        //     while ((s = br.readLine()) != null) {
        //         System.out.println(s);
        //     }
        //     br.close();
        // } catch (Exception e) {
        //     //do nothin
        // }
        
        // List<String> physicalList = 
        
        try {
            List<PhysicalProduct> physicalList = Files.lines(Paths.get("demo/src/data/products.txt"))
            .skip(2)
            .filter(line -> line.contains("PHYSICAL"))
            .map(line -> {
                String[] productData = line.split(",");
                return new PhysicalProduct(productData[0], productData[1], Integer.parseInt(productData[2]), Double.parseDouble(productData[3]), Double.parseDouble(productData[4])); 
            })
            .collect(Collectors.toList());
            // .forEach(System.out::println);
            System.out.println(physicalList);
        } catch (IOException e) {
            //do nothin
            e.printStackTrace();
        }

    }
}
