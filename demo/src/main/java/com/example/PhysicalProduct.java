package com.example;

public class PhysicalProduct extends Product{
    private double weight;

    public PhysicalProduct(String name, String description, int quantity, double price, double weight) {
        super(name, description, quantity, price);
        this.weight = weight;
        
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
        return "PHYSICAL - " +  name;
    }

    @Override
    public String displayAll() {
        String name = getName();
        String description = getDescription();
        int quantity = getQuantity();
        double price = getPrice();
        double weight = getWeight();
        return "PHYSICAL - " + name + ", quantity: " + quantity+ ", description: " + description+ ", price: " + price+ ", weight: " + weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
