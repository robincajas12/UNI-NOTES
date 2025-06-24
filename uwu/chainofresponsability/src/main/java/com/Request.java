package com;

public class Request {
    private double monto;
    private String descripcion;
    public Request(double monto, String descripcion)
    {
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }
}
