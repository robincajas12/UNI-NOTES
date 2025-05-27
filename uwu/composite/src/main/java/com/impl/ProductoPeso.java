package com.impl;

import com.inter.IPrecio;

public class ProductoPeso implements IPrecio {

	private double peso;
	private double precioPeso;
	private String nombre;
	private String categoria;

	public ProductoPeso(double peso, double precioPeso, String nombre, String categoria) {

		this.peso = peso;
		this.precioPeso = precioPeso;
		this.nombre = nombre;
		this.categoria = categoria;
	}

	@Override
	public double getImporteTotal() {
	
		return precioPeso * peso;
	}

	@Override
	public String toString() {
		return "ProductoPeso{" +
				"peso=" + peso +
				", precioPeso=" + precioPeso +
				", nombre='" + nombre + '\'' +
				", categoria='" + categoria + '\'' +
				'}';
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

}
