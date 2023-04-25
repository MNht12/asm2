package com.example;

public class DigitalProduct extends Product{

    public DigitalProduct(String name, String description, int quantity, double price) {
        super(name, description, quantity, price);
    }

    @Override
    public void setMessage(String msg) {
        throw new UnsupportedOperationException("Unimplemented method 'setMessage'");
    }

    @Override
    public String getMessage() {
        throw new UnsupportedOperationException("Unimplemented method 'getMessage'");
    }

    @Override
    public String toString() {
        String name = getName();
        return "DIGITAL - " +  name;
    }

    // displayAll method to show all product in main
    @Override
    public String displayAll() {
        String name = getName();
        String description = getDescription();
        int quantity = getQuantity();
        double price = getPrice();
        return "DIGITAL - " + name + ", quantity: " + quantity+ ", description: " + description+ ", price: " + price;
    }
    
}
