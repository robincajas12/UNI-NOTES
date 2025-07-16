package com.main;

public class Main {
    public static void main(String[] args) {
        ProductoDescuento miP = new ProductoDescuento();
        miP.setPrecio(150);
        ProductoNormal miPNormal = new ProductoNormal();
        miPNormal.setPrecio(150);
        IVA miiva = new IVA();
        System.out.println(miiva.visit(miP));
       System.out.println(miiva.visit(miPNormal));
    }
}
