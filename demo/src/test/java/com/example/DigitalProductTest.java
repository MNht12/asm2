package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DigitalProductTest {

    @Test
    void testDisplayAll() {
        DigitalProduct digi = new DigitalProduct("video001", "This is video001", 12, 1.9, "free", false);
        assertEquals("DIGITAL - video001, quantity: 12, price: 1.9", digi.displayAll());
    }

    @Test
    void testTaxPercentage() {
        DigitalProduct digi = new DigitalProduct("video001", "This is video001", 12, 1.9, "free", false);
        assertEquals(0, digi.taxPercentage());
    }

    @Test
    void testToString() {
        DigitalProduct digi = new DigitalProduct("video001", "This is video001", 12, 1.9, "free", false);
        assertEquals("DIGITAL - video001", digi.toString());
    }
    
}
