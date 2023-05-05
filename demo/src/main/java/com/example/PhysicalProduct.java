package com.example;
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
        String description = getDescription();
        int quantity = getQuantity();
        double price = getPrice();
        double weight = getWeight();
        return "PHYSICAL - " + name + ", quantity: " + quantity+ ", description: " + description+ ", price: " + price+ ", weight: " + weight;
        // return "PHYSICAL - " + name + ", quantity: " + quantity+ ", description: " + description+ ", price: " + price+ ", weight: " + weight+ ", price coupon: " + getCouponPrice()+ ", percent coupon: " + getPercentCoupon();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Override taxPercentage
     */

    @Override
    public int taxPercentage() {

        String tax = getTax();
        int taxPercentage = 0;
        
        try {
            if (tax.equals("free")) {
                taxPercentage = 0;
            }
            if (tax.equals("standard")) {
                taxPercentage = 10;
            }
            if (tax.equals("luxury")) {
                taxPercentage = 20;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return taxPercentage;
    }
}
