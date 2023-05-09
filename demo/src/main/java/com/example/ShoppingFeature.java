package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ShoppingFeature {
    Set<Product> productSet;

    public ShoppingFeature() {
      productSet = new HashSet<Product>();
    }

    public void setProductSet(Set<Product> set) {
      this.productSet = set;
    }

    public Set<Product> getProducts() {
      return productSet;
    }

    public static DigitalProduct addDigital() {
      // productSet.add(new DigitalProduct(name, description, quantity, price, tax,
      // isGift));
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter name:");
      String name = sc.nextLine();

      System.out.println("Enter description:");
      String description = sc.nextLine();

      System.out.println("Enter quantity:");
      int quantity = sc.nextInt();
      sc.nextLine();

      System.out.println("Enter price:");
      double price = sc.nextDouble();
      sc.nextLine();

      System.out.println("Enter tax:");
      String tax = sc.nextLine();

      System.out.println("Enter isGift(true/false):");
      boolean isGift = sc.nextBoolean();
      sc.nextLine();

      DigitalProduct digital = new DigitalProduct(name, description, quantity, price, tax, isGift);
      return digital;

    }

    public static PhysicalProduct addPhysical() {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter name?");
      String name = sc.nextLine();

      System.out.println("Enter description:");
      String description = sc.nextLine();

      System.out.println("Enter quantity?");
      Integer quantity = sc.nextInt();
      sc.nextLine();

      System.out.println("Enter price?");
      Double price = sc.nextDouble();
      sc.nextLine();

      System.out.println("Enter tax:");
      String tax = sc.nextLine();

      System.out.println("Enter weight?");
      Integer weight = sc.nextInt();
      sc.nextLine();

      System.out.println("Enter gift(choose true/false):");
      boolean isGift = sc.nextBoolean();
      sc.nextLine();

      PhysicalProduct physical = new PhysicalProduct(name, description, quantity, price, weight, tax, isGift);
      return physical;
    }

    public Set<Product> removeProduct() {
      Scanner sc = new Scanner(System.in);
      Product physical = new PhysicalProduct(null, null, 0, 0, 0, null, false);
      Product digital = new DigitalProduct(null, null, 0, 0, null, false);
      boolean running = true;
      String removeProduct = "";
      boolean found = true;
      while (running) {
        System.out.println("Name of the product you want to remove, OR enter QUIT/quit to go back:");
        removeProduct = sc.nextLine();
        for (Product rp : productSet) {
          if (rp.getName().equals(removeProduct)) {
            if (rp instanceof PhysicalProduct) {
              physical = rp;
              running = false;
            } else {
              digital = rp;
              running = false;
            }
          } else if (removeProduct.toLowerCase().equals("quit")) {
            running = false;
            return productSet;
          } else {
            found = false;
          }
        }
        if (!found) {
          System.out.println("No product with name: "+removeProduct);
        }
      }
      if (!physical.getName().equals(null)) {
        productSet.remove(physical);
      } else {
        productSet.remove(digital);
      }
      System.out.println("Product name: " + removeProduct + " has been removed!");
      return productSet;
    }

    public List<ShoppingCart> sortCart(List<ShoppingCart> cartList) {
      cartList.sort(new SortByWeight());
      return cartList;
    }

    public Set<Product> editProducts() {
      Scanner sc = new Scanner(System.in);
      System.out.println("Choose a product to edit:");
      String ep = sc.nextLine();
      for (Product p : productSet) {
        if (p.getName().equals(ep)) {
          if (p instanceof PhysicalProduct) {
            System.out.println("Choose an attribute to change:\n" +
                "1.Description\n" +
                "2.Price\n" +
                "3.Quantity\n" +
                "4.Weight\n");
            int atb = sc.nextInt();
            sc.nextLine();
            switch (atb) {
              case 1: {
                System.out.println("Input your Description:");
                String newdes = sc.nextLine();
                p.setDescription(newdes);
                break;
              }
              case 2: {
                System.out.println("Input your Price:");
                double newpri = sc.nextDouble();
                sc.nextLine();
                p.setPrice(newpri);
                break;
              }
              case 3: {
                System.out.println("Input your Quantity:");
                int newqua = sc.nextInt();
                sc.nextLine();
                p.setQuantity(newqua);
                break;
              }
              case 4: {
                System.out.println("Input your Weight:");
                double newwei = sc.nextInt();
                sc.nextLine();
                ((PhysicalProduct) p).setWeight(newwei);
                break;
              }
            }
          } else {
            System.out.println("Choose an attribute to change:\n" +
                "1.Description\n" +
                "2.Price\n" +
                "3.Quantity\n");
            int atb = sc.nextInt();
            sc.nextLine();
            switch (atb) {
              case 1: {
                System.out.println("Input your Description:");
                String newdes = sc.nextLine();
                p.setDescription(newdes);
                break;
              }
              case 2: {
                System.out.println("Input your Price:");
                double newpri = sc.nextDouble();
                sc.nextLine();
                p.setPrice(newpri);
                break;
              }
              case 3: {
                System.out.println("Input your Quantity:");
                int newqua = sc.nextInt();
                sc.nextLine();
                p.setQuantity(newqua);
                break;
              }
            }
          }
        }
      }
      return productSet;
    }
}