package com.impl;

import com.anotation.Adaptable;

@Adaptable(metodo = "manejarAuto")
public class Auto {
    public void manejarAuto(){
        System.out.println("Driving auto");
    }
}
