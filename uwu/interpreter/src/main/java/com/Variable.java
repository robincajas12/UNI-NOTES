package com;

import java.util.Map;

public class Variable implements Expresiones{
    private final String nombre;

    Variable(String nombre)
    {
        this.nombre = nombre;
    }
    @Override
    public int interprete(Map<String, Integer> vars) {
        return vars.get(nombre);
    }
}
