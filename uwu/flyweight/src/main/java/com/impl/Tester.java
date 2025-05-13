package com.impl;

import com.interfaces.IEmpleado;

public class Tester implements IEmpleado
{
    private final  String trabajo;
    private String habilidad;
    public  Tester()
    {
        this.trabajo = "testeo de los programas";
    }
    @Override
    public void habilidadAsignada(String habilidad) {
        this.habilidad = habilidad;
    }

    @Override
    public void tarea() {
        System.out.printf("%s con habildiad asignada %s con trabajo de %s \n",this.getClass().getSimpleName(),this.habilidad, this.trabajo);
    }
}
