package com.models;

import com.anotation.AColor;
import com.interfaces.IColor;
@AColor(name = "naranja")
public class Naranja implements IColor {
    @Override
    public void pintar(int borde) {
        System.out.println("Se pinta de color "+ this.getClass().getSimpleName()+" con border" + borde + " cm");
    }
}
