package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PhysicalProductTest {

    @Test
    void testDisplayAll() {
        PhysicalProduct physi = new PhysicalProduct("pen001", "This is pen001", 23, 2.4, 0.4, "standard", true);
        assertEquals("PHYSICAL - pen001, quantity: 23, price: 2.4, weight: 0.4", physi.displayAll());
    }

    @Test
    void testTaxPercentage() {
        PhysicalProduct physi = new PhysicalProduct("pen001", "This is pen001", 23, 2.4, 0.4, "standard", true);
        assertEquals(10, physi.taxPercentage());
    }

    @Test
    void testToString() {
        PhysicalProduct physi = new PhysicalProduct("pen001", "This is pen001", 23, 2.4, 0.4, "standard", true);
        assertEquals("PHYSICAL - pen001", physi.toString());
    }

}