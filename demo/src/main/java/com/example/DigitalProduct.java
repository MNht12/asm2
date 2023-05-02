package com.example;

public class DigitalProduct extends Product{

    public DigitalProduct(String name, String description, int quantity, double price, String tax, Boolean isGift) {
        super(name, description, quantity, price, tax, isGift);
    }

    @Override
    public String toString() {
        String name = getName();
        return "DIGITAL - " +  name;
    }

    // displayAll method to show all info of a product
    @Override
    public String displayAll() {
        String name = getName();
        String description = getDescription();
        int quantity = getQuantity();
        double price = getPrice();
        return "DIGITAL - " + name + ", quantity: " + quantity+ ", description: " + description+ ", price: " + price;
        // return "DIGITAL - " + name + ", quantity: " + quantity+ ", description: " + description+ ", price: " + price+ ", price coupon: " + getCouponPrice()+ ", percent coupon: " + getPercentCoupon();
    }
}
