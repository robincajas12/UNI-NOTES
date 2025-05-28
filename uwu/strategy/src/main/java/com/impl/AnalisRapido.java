package com.impl;

import com.inter.IAnalize;

public  abstract class AnalisRapido implements IAnalize {
    @Override
    public void analizar()
    {
        iniciar();
        saltarComprimidos();
        detener();
    }
    public abstract void iniciar();
    public abstract void saltarComprimidos();
    public abstract void detener();
}
