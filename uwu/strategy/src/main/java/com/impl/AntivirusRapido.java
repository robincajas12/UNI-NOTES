package com.impl;

import java.util.Random;

public class AntivirusRapido extends AnalisRapido{
    @Override
    public void iniciar() {
        System.out.println("iniciando antivirus rapido analisis");
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saltarComprimidos() {
        System.out.println("saltando comprimidos");
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
            System.out.println("todos los comprimidos fueron saltados");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void detener() {
        System.out.println("deteniendo antivirus rápido analisis");
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
