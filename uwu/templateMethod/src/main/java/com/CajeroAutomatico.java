package com;

public class CajeroAutomatico extends CuentaBancaria {
    public CajeroAutomatico(String c, int valor, int tipo) {
        procesar(c, valor, tipo);
    }

    @Override
    public void confirmar() {
        System.out.println("hola lol");
    }
}
