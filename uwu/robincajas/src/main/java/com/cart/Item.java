package com.cart;
// lo uso para el Composite y el Strategy
public interface Item {
    double getPrice();
    int quantity();
    String inforForFactura();
}
