package com.products;

public interface Product<T> {
    //: nombre, precio, tipo, fecha
    //de caducidad y número de lote
    String name();
    double price();
    T type();
    String expireDate();
    int loteNumber();
    String countryOfOrigin();
    String manufacturingDate();
}
