package com;

import com.inter.IOperacionesCadenas;
import com.impl.Operaciones;
import com.impl.OperacionesCadenas;
import com.impl.OperacionesFactory;
import static com.impl.OperacionesFactory.create;
import static com.impl.OperacionesFactory.register;


import com.inter.OperacionesNumeros;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println("UwU");
        register(OperacionesNumeros.class, Operaciones.class);
        register(IOperacionesCadenas.class, OperacionesCadenas.class);

        OperacionesNumeros on = create(OperacionesNumeros.class);
        on.restar(1,5);
        on.sumar(5,5);
        IOperacionesCadenas oc = create(IOperacionesCadenas.class);
        oc.restar(5,6,6,7,8);
        oc.sumar(44,4,4,44,4);
    }
}
