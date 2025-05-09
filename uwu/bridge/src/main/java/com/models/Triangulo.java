package com.models;

import com.anotation.AColor;
import com.anotation.AFigure;
import com.interfaces.IColor;
@AFigure(name = "triangulo")
public class Triangulo extends Figura {

    public Triangulo(IColor color)
    {
        super(color);

    }
    @Override
    public void dibujarFigura(int border) {
        System.out.println("triangulo con : " + border );
        color.pintar(border);
    }

    @Override
    public void modificarBorde(int border) {
        System.out.println("se modifica el borde con border " + border);
        dibujarFigura(border);
    }
}
