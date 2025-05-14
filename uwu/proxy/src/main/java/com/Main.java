package com;

import com.impl.Operaciones;
import com.impl.OperacionesCadenas;
import com.impl.OperacionesFactory;
import com.impl.OperacionesNumerosProxy;
import com.inter.OperacionesNumeros;

public class Main {
    public static void main(String[] args) {
        System.out.println("UwU");
        OperacionesNumerosProxy proxy = new OperacionesNumerosProxy();
        int resSuma = proxy.sumar(5,2);
        int resResta = proxy.restar(1,5);
        OperacionesNumeros on = OperacionesFactory.createOperation(new Operaciones());
        on.restar(1,5);
        OperacionesCadenas oc = OperacionesFactory.createOperationesCadenas(new OperacionesCadenas());
        oc.restar(5,6,6,7,8);
    }
}
