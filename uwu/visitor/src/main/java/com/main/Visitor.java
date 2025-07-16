package com.main;

public interface Visitor {
    double visit(ProductoNormal productoNormal);
    double visit(ProductoDescuento productoDescuento);
}
