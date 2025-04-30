package com.impl;

import com.anotation.Theme;
import com.inter.Boton;
import com.inter.UIFactory;
@Theme("dark")
public class FactoryImplObcuro implements UIFactory {
    @Override
    public Boton crearBoton() {
        return new BotonObscuro();
    }
}
