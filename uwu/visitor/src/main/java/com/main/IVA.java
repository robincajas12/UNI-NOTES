package com.main;

public class IVA implements Visitor{
    private static final double impNormal = 1.12;
    private static final double descuento = 1.85;
    @Override
    public double visit(ProductoNormal productoNormal) {
        return productoNormal.getPrecio() * impNormal;
    }

    @Override
    public double visit(ProductoDescuento productoDescuento) {
        return productoDescuento.getPrecio() * descuento;
    }
}
