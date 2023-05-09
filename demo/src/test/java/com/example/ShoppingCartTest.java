package com.example;

/**
 * @author Nguyen Minh Nhat, Nguyen Cong Thinh, Nguyen Dang Ha, Don Tuan Duong
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ShoppingCartTest {
    @Test
    void testAddCoupon() {
        ShoppingCart cart = new ShoppingCart();
        List<String> allCoupon = new ArrayList<>();
        DigitalProduct digitalProduct = new DigitalProduct("video2", "video2 des", 5, 1, "free", true);
        Set<Product> productSet = new HashSet<>();
        productSet.add(digitalProduct);
        cart.setProductSet(productSet);
        digitalProduct.setCouponPrice(2.0, "pricevideo2");
        String[] item = {"video2","5","null"};
        String[] cartCoupon = {"null",};
        cart.setData(cartCoupon);
        cart.setData(item);
        String coupon = "percentPen10";
        allCoupon.add(coupon);
        assertEquals(false, cart.addCoupon(coupon));
    }

    @Test
    // add item return true
    void testAddItemTrue() {
        ShoppingCart cart = new ShoppingCart();
        DigitalProduct digitalProduct = new DigitalProduct("video2", "video2 des", 5, 1, "free", true);
        Set<Product> productSet = new HashSet<>();
        productSet.add(digitalProduct);
        cart.setProductSet(productSet);
        String[] item = {"video2","5","null"};
        cart.setData(item);
        assertEquals(true, cart.addItem("video2","1"));
    }

    @Test
    // add item return false
    void testAddItemFalse() {
        ShoppingCart cart = new ShoppingCart();
        DigitalProduct digitalProduct = new DigitalProduct("video2", "video2 des", 5, 1, "free", true);
        Set<Product> productSet = new HashSet<>();
        productSet.add(digitalProduct);
        cart.setProductSet(productSet);
        String[] item = {"video2","5","null"};
        cart.setData(item);
        assertEquals(false, cart.addItem("video2","10"));
    }

    @Test
    void testCalculateWeight() {
        ShoppingCart cart = new ShoppingCart();
        PhysicalProduct physicalProduct = new PhysicalProduct("pen2", "pen2 des", 2, 1, 10, "free", false);
        Set<Product> productSet = new HashSet<>();
        productSet.add(physicalProduct);
        cart.setProductSet(productSet);
        String[] coupon = {};
        String[] item = {"pen2","1","null"};
        cart.setData(coupon);
        cart.setData(item);
        assertEquals(physicalProduct.getWeight(), cart.calculateWeight());
    }

    @Test
    void testGetCoupon() {
        ShoppingCart cart = new ShoppingCart();
        PhysicalProduct physicalProduct = new PhysicalProduct("pen2", "pen2 des", 2, 1, 10, "free", false);
        Set<Product> productSet = new HashSet<>();
        physicalProduct.setCouponPrice(2.0, "pricepen2");
        productSet.add(physicalProduct);
        cart.setProductSet(productSet);
        String[] coupon = {"pricepen2"};
        String[] item = {"pen2","1","null"};
        cart.setData(coupon);
        cart.setData(item);
        String[] couponData = {"double, 2.0"}; // getCoupon method will return String[] with coupon type and value
        assertEquals(Arrays.toString(couponData), Arrays.toString(cart.getCoupon()));
    }

    @Test
    void testGetMessage() {
        ShoppingCart cart = new ShoppingCart();
        PhysicalProduct physicalProduct = new PhysicalProduct("pen2", "pen2 des", 2, 1, 10, "free", false);
        Set<Product> productSet = new HashSet<>();
        physicalProduct.setCouponPrice(2.0, "pricepen2");
        productSet.add(physicalProduct);
        cart.setProductSet(productSet);
        String[] coupon = {"pricepen2"};
        String[] item = {"pen2","1","this is mess"};
        cart.setData(coupon);
        cart.setData(item);
        assertEquals("Product item: pen2's' gift message: this is mess", cart.getMessage("pen2"));
    }

    @Test
    void testTaxPercentage() {
        ShoppingCart cart = new ShoppingCart();
        PhysicalProduct physicalProduct = new PhysicalProduct("pen2", "pen2 des", 2, 1, 10, "free", false);
        Set<Product> productSet = new HashSet<>();
        physicalProduct.setCouponPrice(2.0, "pricepen2");
        productSet.add(physicalProduct);
        cart.setProductSet(productSet);
        String[] coupon = {"pricepen2"};
        String[] item = {"pen2","1","this is mess"};
        cart.setData(coupon);
        cart.setData(item);
        assertEquals(0, cart.taxPercentage(physicalProduct));
    }
}
