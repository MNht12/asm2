package com.example;

public interface isCoupon {
    // set coupon to a discount value
    public void setPercentCoupon(int value, String code);
    public void setCouponPrice(Double value, String code);

    // return discount value
    public int getPercentCoupon();
    public Double getCouponPrice();

    public String getPriceCode();
    public String getPercentCode();

}
