package com.refl;

import java.lang.reflect.Field;
import java.util.List;

import com.impl.ProductoCompuesto;
import com.impl.ProductoPeso;
import com.impl.ProductoUnitario;
import com.inter.IPrecio;
import com.model.Pedido;

public class InspectorPrecio {

	public static void imprimirDetalles(IPrecio prod) {
		Class<?> clase = prod.getClass();
		System.out.println("Clase:" + clase.getName());
		if(!(prod instanceof ProductoCompuesto)) {


			Field[] fields = clase.getDeclaredFields();
			for (Field it : fields) {
				it.setAccessible(true);
				try {
					Object valor = it.get(prod);
					System.out.println("" + it.getName() + " = " + valor);

				} catch (IllegalAccessException e) {
					System.out.println("No se puede acceder " + it.getName() + "");
				}

			}
			if ((prod instanceof ProductoCompuesto)) {
				List<IPrecio> subPrecio = ((ProductoCompuesto) prod).getProducto();
				for (IPrecio it : subPrecio) {
					imprimirDetalles(it);
				}
			}
		}

	}

	public void cambiarPrecio(Pedido pedido, String nombre, double newPrecio) throws Exception {
		for (IPrecio it : pedido.getProducto()) {
			cambiarPrecioRecursivo(it, nombre, newPrecio);
		}
	}

	public static void cambiarPrecioRecursivo(IPrecio prod, String nombre, double newPrecio) throws Exception{
		Class<?> clase = prod.getClass();
		try {
			Field nameField = clase.getDeclaredField("nombre");
			var fields = clase.getDeclaredFields();


			nameField.setAccessible(true);
			Object nombreValor= nameField.get(prod);
			if(nombreValor!=null && nombreValor.equals(nombre)) {
				Field prodField = null;
				if(prod instanceof ProductoUnitario  ) {
					prodField = clase.getDeclaredField("precio");
				}else if(prod instanceof ProductoPeso) {
					prodField = clase.getDeclaredField("precioPeso");
				}
				if (prodField != null) {
					prodField.setAccessible(true);
					prodField.set(prod, newPrecio);
					System.out.println("Precio canbiado: "+nombre+":" +newPrecio);
				}

			}
		}catch(IllegalAccessException e) {


		}
	}

}
