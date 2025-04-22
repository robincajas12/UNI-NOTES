package com;


import com.models.Producto;
import com.services.ImplServices;

import java.beans.Statement;

public class Main {

	public static void main(String[] args) {
		System.out.println("aqaa");
		ImplServices miS = new ImplServices();



		miS.create(new Producto(0,"Peluche", 3*100, 45));

	}

}
