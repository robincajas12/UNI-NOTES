package com.impl;

import java.util.Random;

public class AntivirusCompleto extends AnalisisCompleto {


    @Override
    public void iniciar() {
        System.out.println("iniciando antivirus completo");
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void analizarMemoria() {
        System.out.println("analizando memoria");
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void analizarKeyLoggers() {
        System.out.println("analizando key loggers");
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void analizarRootKits() {
        System.out.println("analizando root kits");
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void descomprimir() {
        System.out.println("descomprimiendo files");
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void detener() {
        System.out.println("deteniendo analisis completo");
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
