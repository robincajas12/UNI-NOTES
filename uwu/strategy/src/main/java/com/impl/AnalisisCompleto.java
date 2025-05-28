package com.impl;

import com.inter.IAnalize;

public abstract class AnalisisCompleto implements IAnalize
{
    @Override
    public void analizar()
    {
        iniciar();
        analizarMemoria();
        analizarKeyLoggers();
        analizarRootKits();
        descomprimir();
        detener();
    }
    public abstract void iniciar();
    public abstract void analizarMemoria();
    public abstract void analizarKeyLoggers();
    public abstract void analizarRootKits();
    public abstract void descomprimir();
    public abstract void detener();
}
