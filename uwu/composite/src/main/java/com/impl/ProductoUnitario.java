package com.impl;

import com.inter.IPrecio;

public class ProductoUnitario implements IPrecio {
	private String nombre;
	private String categoria;
	private int cantidad;
	private double precio;

	public ProductoUnitario(String nombre, String categoria, int cantidad, double precio) {
		this.nombre = nombre;
		this.categoria = categoria;
		this.cantidad = cantidad;
		this.precio = precio;

	}

	@Override
	public double getImporteTotal() {

		return precio * cantidad;
	}

	@Override
	public String toString() {
		return this.nombre + this.precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
