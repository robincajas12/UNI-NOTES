package com.impl;

import com.inter.OperacionesNumeros;

public class Operaciones implements OperacionesNumeros {
    @Override
    public int sumar(int n1, int n2) {
        return n1+n2;
    }

    @Override
    public int restar(int n1, int n2) {
        return n1-n2;
    }
}
