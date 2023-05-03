package com.example;
public abstract class Product implements isCoupon{
    private String name;
    private String description;
    private int quantity;
    private double price;
    private String tax;
    private boolean isGift;

    private int precentCoupon;
    private Double priceCoupon;
    private String priceCode;
    private String percentCode;

    public Product(String name, String description, int quantity, double price, String tax, Boolean isGift) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
        this.isGift = isGift;
    }

    // Override isCoupon interface methods
    @Override
    public void setCouponPrice(Double value, String code) {
        priceCoupon = value;
        priceCode = code;
    }

    @Override
    public void setPercentCoupon(int value, String code) {
        precentCoupon = value;
        percentCode = code;
    }

    @Override
    public Double getCouponPrice() {
        return priceCoupon;
    }

    @Override
    public int getPercentCoupon() {
        return precentCoupon;
    }

    @Override
    public String getPriceCode() {
        return priceCode;
    }

    @Override
    public String getPercentCode() {
        return percentCode;
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

    public boolean getisGift() {
        return isGift;
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

    public void setGift(boolean isGift) {
        this.isGift = isGift;
    }

    abstract public String toString();

    abstract public String displayAll();
    
}
