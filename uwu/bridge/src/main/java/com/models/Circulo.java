package com.models;

import com.anotation.AFigure;
import com.interfaces.IColor;
@AFigure(name = "circulo")
public class Circulo extends Figura {
    Circulo(IColor color) {
        super(color);
    }

    @Override
    public void dibujarFigura(int border) {
        color.pintar(border);
        System.out.println("Dibujando Circulo con " + border + " cm de border");
    }

    @Override
    public void modificarBorde(int border) {
        System.out.println("se modifica el border "+border);
        dibujarFigura(border);
    }
}
