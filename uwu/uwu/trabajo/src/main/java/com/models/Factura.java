package com.models;

import com.anotations.FactoryAnnotation;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FactoryAnnotation(name = "Factura")
public class Factura {
    List<DatosCompra> data = new ArrayList<>();
    public Factura()
    {
    }

    public  String printData(DatosCompra datosCompra)
    {
        return "Nombre producto: " + datosCompra.getNombre() +" \n"+
                "Precio: " + datosCompra.getPrecio() +" \n"+
                "Precio: " + datosCompra.getNombreProducto() +" \n";
    }
    @Override
    public  String toString()
    {
        return data.stream().reduce((prev, e, x )-> printData(x));
        ""
    }
}
