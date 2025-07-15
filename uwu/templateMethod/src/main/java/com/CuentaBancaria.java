package com;

public abstract class CuentaBancaria {
    String cuenta;
    int saldo;
    public void setCuenta(String cuenta)
    {
        this.cuenta = cuenta;
    }
    public void depositar(int val)
    {
        this.saldo += val;
    }
    public void retirar(int val)
    {
        if((saldo - val) >=0)
        {
            this.saldo -= val;
        }
       else {
            System.out.println("salgo insuficiente:" + val);
        }
    }
    public void consultarSaldo()
    {
        System.out.println("Salto de cuenta: " + this.saldo);
    }

    public void auditoria()
    {
        System.out.println("se ha completaod transaccion");
    }
    public abstract void confirmar();
    public void procesar(String c, int val, int tipo)
    {
        confirmar();
        setCuenta(c);
        switch (tipo)
        {
            case 1:
                depositar(val);
                break;
            case 2:
                retirar(val);
                break;
            default:
                System.out.println("invalid option");
                break;

        }
        consultarSaldo();
        auditoria();
    }
}
