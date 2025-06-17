package com;

public class TareaImprimirProducto implements TareaProducto{
    @Override
    public void ejecutar(Producto producto) {
        System.out.println(producto.id );
        System.out.println(producto.nombre);
        System.out.println(producto.valor);
    }
}
