package com.example;

public interface isCoupon {
    // set coupon to a discount value
    public void setPercentCoupon(int value);
    public void setCouponPrice(Double value);

    // return discount value
    public int getPercentCoupon();
    public Double getCouponPrice();
    
    // set coupon to a unique String value
    public void setPriceString(String code);
    public void setPercentString(String code);


}
