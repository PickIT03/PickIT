package com.example.pickit.shopowner;

public class PlaceOrderHelperClass {
    private String productname;
    private int quantity;


    @Override
    public String toString() {
        return "PlaceOrderHelperClass{" +
                "productname='" + productname + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PlaceOrderHelperClass(String productname, int quantity) {
        this.productname = productname;
        this.quantity = quantity;
    }
}
