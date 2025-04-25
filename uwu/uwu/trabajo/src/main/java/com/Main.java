package com;

import com.db.services.ImplServices;
import com.implementations.FactoryImplementation;
import com.models.DatosCompra;
import com.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        FactoryImplementation miFactory = new FactoryImplementation();
        miFactory.init("com/models");
        ImplServices impl = new ImplServices();
        List<DatosCompra>  data = new ArrayList<>();
        System.out.println("Tienda");
        Product product = Product.builder()
                .nombre("Helado")
                .precio(50)
                .cantidad(50)
                .build();
        impl.create(product);

        for(Product p : impl.listar())
        {
            impl.delete(p.getId());
            DatosCompra datosCompra = miFactory.create(DatosCompra.class);
            datosCompra.setNombre("Usuario final");
            datosCompra.setPrecio(p.getPrecio());
            datosCompra.setNombreProducto(p.getNombre());
            data.add(datosCompra);
        }


    }
}
