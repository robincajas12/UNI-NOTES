package com;

import java.util.Map;

public class Multitplicacion implements Expresiones{
    private final Expresiones izquierda;
    private final Expresiones derecha;
    public Multitplicacion(Expresiones izquierda, Expresiones derecha)
    {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }
    @Override
    public int interprete(Map<String, Integer> vars) {
        return izquierda.interprete(vars) * derecha.interprete(vars);
    }
}
