package com.impl;

import java.util.ArrayList;

import com.inter.IPrecio;

public class ProductoCompuesto implements IPrecio {
	private ArrayList<IPrecio> producto;
	private String nombre;
	public ProductoCompuesto() {
		producto = new ArrayList<IPrecio>();

	}

	public void addProducto(IPrecio prod) {
		producto.add(prod);
	}

	public void removeProducto(IPrecio prod) {
		producto.remove(prod);
	}

	@Override
	public double getImporteTotal() {
		double tot = 0;

		for (var it : producto) {

			tot += it.getImporteTotal();


		}

		return tot;
	}

	public ArrayList<IPrecio> getProducto() {
		return producto;
	}

	public void setProducto(ArrayList<IPrecio> producto) {
		this.producto = producto;
	}

}
