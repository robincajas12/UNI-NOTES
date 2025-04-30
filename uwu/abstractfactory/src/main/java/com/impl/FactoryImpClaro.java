package com.impl;

import com.anotation.Theme;
import com.inter.Boton;
import com.inter.UIFactory;
@Theme("light")
public class FactoryImpClaro implements UIFactory {
    @Override
    public Boton crearBoton() {
        return new BotonClaro();
    }
}
