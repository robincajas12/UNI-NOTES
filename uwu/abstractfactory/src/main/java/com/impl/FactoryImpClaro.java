package com.impl;

import com.inter.Boton;
import com.inter.UIFactory;

public class FactoryImpClaro implements UIFactory {
    @Override
    public Boton crearBoton() {
        return new BotonClaro();
    }
}
