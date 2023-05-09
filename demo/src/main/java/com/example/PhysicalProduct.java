package com.example;
/**
 * @author Nguyen Minh Nhat, Nguyen Cong Thinh, Nguyen Dang Ha, Don Tuan Duong
 */

public class PhysicalProduct extends Product{
    private double weight;

    public PhysicalProduct(String name, String description, int quantity, double price, double weight, String tax, boolean isGift) {
        super(name, description, quantity, price, tax, isGift);
        this.weight = weight;
        
    }
    
    @Override
    public String toString() {
        String name = getName();
        return "PHYSICAL - " +  name;
    }

    // displayAll method to show all info of a product
    @Override
    public String displayAll() {
        String name = getName();
        int quantity = getQuantity();
        double price = getPrice();
        double weight = getWeight();
        return "PHYSICAL - " + name + ", quantity: " + quantity+ ", price: " + price+ ", weight: " + weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int taxPercentage() {

        String tax = getTax();
        int taxPercentage = 0;

        if (tax.equals("free")) {
            taxPercentage = 0;
        } else if (tax.equals("standard")) {
            taxPercentage = 10;
        } else if (tax.equals("luxury")) {
            taxPercentage = 20;
        } else {
            System.out.println("Invalid value output!");
        }
        return taxPercentage;
    }
}
