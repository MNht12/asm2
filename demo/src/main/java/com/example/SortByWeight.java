package com.example;

import java.util.Comparator;

public class SortByWeight implements Comparator<ShoppingCart>{

    @Override
    public int compare(ShoppingCart o1, ShoppingCart o2) {
        return (int)(o1.calculateWeight() - o2.calculateWeight());
    }
}
