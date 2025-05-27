package com;

import com.model.Pedido;
import com.impl.ProductoCompuesto;
import com.impl.ProductoPeso;
import com.impl.ProductoUnitario;
import com.model.Cliente;
import com.refl.InspectorPrecio;

public class AppComposite {

	public static void main(String[] args) throws Exception {

		Pedido ped = new Pedido(Cliente.PEROR);
		ProductoUnitario cola = new ProductoUnitario("Cola", "Gaseosa", 8, 2.30);
		ProductoUnitario atun = new ProductoUnitario("Atub", "Enlatados", 9, 1.30);
		ProductoUnitario fideo = new ProductoUnitario("Fideo", "Pasta", 5, 5.30);
		ProductoPeso jamon = new ProductoPeso(5.5, 1.15, "Jamon", "Embutido");// (double peso, double precioPeso, String
																				// nombre, String categoria
		ProductoPeso papas = new ProductoPeso(2.6, 0.30, "Papas", "Tuberculo");
		ProductoPeso arroz = new ProductoPeso(3.5, 0.60, "Arroz", "Cereal");

		ProductoCompuesto canasta = new ProductoCompuesto();
		canasta.addProducto(arroz);
		canasta.addProducto(fideo);
		canasta.addProducto(jamon);

		ped.addProducto(papas);
		ped.addProducto(cola);
		ped.addProducto(atun);
		ped.addProducto(canasta);
		System.out.println("Total: " + ped.getImporteTotal());
		InspectorPrecio.imprimirDetalles(canasta);
		InspectorPrecio.cambiarPrecioRecursivo(ped, "Cola", 4.5);

	}

}
