package com.models;

import com.anotations.FactoryAnnotation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FactoryAnnotation(name = "Datos Compra")
public class DatosCompra {
    private String nombre;
    private Integer precio;
    private String nombreProducto;
    public  DatosCompra()
    {

    }

}
