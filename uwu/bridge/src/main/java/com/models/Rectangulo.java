package com.models;

import com.anotation.AFigure;
import com.interfaces.IColor;
@AFigure(name = "rectangulo")
public class Rectangulo extends Figura {
    Rectangulo(IColor color) {
        super(color);
    }

    @Override
    public void dibujarFigura(int border) {
        System.out.println("Pintando cuadrado con " + border + " cm de border");
        color.pintar(border);
    }

    @Override
    public void modificarBorde(int border) {
        System.out.println("modificando border" + border + " cm ");
        dibujarFigura(border);
    }
}
