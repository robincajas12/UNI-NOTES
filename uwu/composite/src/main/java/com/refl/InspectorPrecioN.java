package com.refl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.impl.ProductoCompuesto;
import com.impl.ProductoPeso;
import com.impl.ProductoUnitario;
import com.inter.IPrecio;
import com.model.Pedido;

public class InspectorPrecioN {

    public static void imprimirDetalles(IPrecio prod) {
        Class<?> clase = prod.getClass();
        System.out.println("Clase: " + clase.getName());

        if (!(prod instanceof ProductoCompuesto)) {

            Arrays.stream(clase.getDeclaredFields())
                    .peek(f -> f.setAccessible(true))
                    .forEach(field -> {
                        try {
                            Object valor = field.get(prod);
                            System.out.println(field.getName() + " = " + valor);
                        } catch (IllegalAccessException e) {
                            System.out.println("No se puede acceder " + field.getName());
                        }
                    });
        } else {

            List<IPrecio> subProductos = ((ProductoCompuesto) prod).getProducto();
            subProductos.stream()
                    .forEach(InspectorPrecio::imprimirDetalles);
        }


    }

    public void cambiarPrecio(Pedido pedido, String nombre, double newPrecio) throws Exception {
        pedido.getProducto().stream()
                .forEach(iprecio -> {
                    try {
                        cambiarPrecioRecursivo(iprecio, nombre, newPrecio);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void cambiarPrecioRecursivo(IPrecio prod, String nombre, double newPrecio) throws Exception {
        Class<?> clase = prod.getClass();

        try {
            Field nameField = clase.getDeclaredField("nombre");
            nameField.setAccessible(true);
            Object nombreValor = nameField.get(prod);

            if (nombreValor != null && nombreValor.equals(nombre)) {
                Field prodField = null;
                if (prod instanceof ProductoUnitario) {
                    prodField = clase.getDeclaredField("precio");
                } else if (prod instanceof ProductoPeso) {
                    prodField = clase.getDeclaredField("precioPeso");
                }

                if (prodField != null) {
                    prodField.setAccessible(true);
                    prodField.set(prod, newPrecio);
                    System.out.println("Precio cambiado: " + nombre + ": " + newPrecio);
                }
            }

            if (prod instanceof ProductoCompuesto) {
                List<IPrecio> subProductos = ((ProductoCompuesto) prod).getProducto();
                subProductos.stream()
                        .forEach(subProd -> {
                            try {
                                cambiarPrecioRecursivo(subProd, nombre, newPrecio);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
