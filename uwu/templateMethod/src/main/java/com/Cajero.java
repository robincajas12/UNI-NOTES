package com;

public class Cajero extends CuentaBancaria{
    public Cajero(String c, int valor,int tipo)
    {
        procesar(c, valor, tipo);
    }
    @Override
    public void confirmar() {
        System.out.println("Welcome, how can I help you today!");
    }

    @Override
    public void auditoria()
    {
        System.out.println("Hola Como puedo ayudarle? ");
        super.auditoria();
    }
}
