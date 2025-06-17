package com;

public class TareaEnviarCorreo implements TareaProducto{
    @Override
    public void ejecutar(Producto producto) {
        System.out.println("enviando " + producto.id);
    }
}
