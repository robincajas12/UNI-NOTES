package com.models;

import com.interfaces.IColor;

public abstract class Figura {
    IColor color;
    Figura(IColor color)
    {
        this.color = color;
    }
    abstract public void dibujarFigura(int border);
    public abstract void modificarBorde(int border);
}
