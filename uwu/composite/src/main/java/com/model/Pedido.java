package com.model;

import com.impl.ProductoCompuesto;
import com.impl.ProductoPeso;
import com.impl.ProductoUnitario;
import com.inter.IPrecio;
import com.model.Cliente;

public class Pedido extends ProductoCompuesto {
	private Cliente cliente;

	public Pedido(Cliente cliente) {
		this.cliente = cliente;

	}

	public void addProducto(IPrecio prodt) {
		super.addProducto(prodt);
	}

	public void remmoveProducto(IPrecio prodt) {
		super.removeProducto(prodt);
	}

	public void establecerCantidad(IPrecio prod, int cantidad) {
		if (prod instanceof ProductoUnitario) {
			ProductoUnitario P = (ProductoUnitario) prod;
			P.setCantidad(cantidad);
		}
	}

	public void establecerPeso(IPrecio prod, double peso) {
		if (prod instanceof ProductoPeso) {
			ProductoPeso P = (ProductoPeso) prod;
			P.setPeso(peso);
		}
	}

}
