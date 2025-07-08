package com;

import java.util.Map;

public class Numero implements Expresiones{
    private final int numero;

    public Numero(int numero)
    {
        this.numero = numero;
    }
    @Override
    public int interprete(Map<String, Integer> vars) {
        return numero;
    }
}
