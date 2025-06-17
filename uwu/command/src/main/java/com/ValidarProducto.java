package com;

public class ValidarProducto implements TareaProducto{
    @Override
    public void ejecutar(Producto producto) {
        if(producto.getValor() > 100f && producto.getValor() < 1000f)
        {
            System.out.println("Producto accede a descuento ");
        }else {
            System.out.println("no aplica descuento");
        }
    }
}
