package com.example;

import java.time.Period;

public abstract class Product implements isGift, isCoupon{
    private String name;
    private String description;
    private int quantity;
    private double price;
    private String tax;

    private String message;

    private int precent;
    private Double priceCoupon;
    private String priceCode;
    private String percentCode;

    public Product(String name, String description, int quantity, double price, String tax, String priceCode, String percentCode) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
        this.priceCode = priceCode;
        this.percentCode = percentCode;
    }

    // Override isGift interface methods
    @Override
    public void setMessage(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    // Override isCoupon interface methods
    @Override
    public Double getCouponPrice() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPercentCoupon() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public void setPercentString(String code) {
        this.percentCode = code;
        
    }

    @Override
    public void setPriceString(String code) {
        this.priceCode = code;
    }

    @Override
    public void setCouponPrice(Double value) {
        priceCoupon = value;
    }

    @Override
    public void setPercentCoupon(int value) {
        precent = value;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public String getPercentCode() {
        return percentCode;
    }

    // Setter methods
    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTax() {
        return tax;
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    public void setPercentCode(String percentCode) {
        this.percentCode = percentCode;
    }

    abstract public String toString();

    abstract public String displayAll();
    
}
