package com.impl;

import com.inter.OperacionesNumeros;

public class OperacionesNumerosProxy implements OperacionesNumeros {
    Operaciones op;
    public OperacionesNumerosProxy()
    {
        this.op = new Operaciones();
    }
    @Override
    public int sumar(int n1, int n2) {
        System.out.println("proxy haciendo validaciones en suma");
        return op.sumar(n1, n2);
    }

    @Override
    public int restar(int n1, int n2) {
        System.out.println("proxy haciendo validaciones en resta");
        return op.restar(n1,n2);
    }
}
