package com;

public class Producto {
    private static int count;
  int id;
  String nombre;
  double valor;

  static {
      count = 124;
  }

  public Producto(String nombre, double valor)
  {
      this.id = count++;
      this.nombre  = nombre;
      this.valor = valor;
  }

  public double getValor() {
      return valor;
  }

  public void setValor(double valor) {
      this.valor = valor;
  }


}
