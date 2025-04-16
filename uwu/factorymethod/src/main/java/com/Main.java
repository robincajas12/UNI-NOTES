package com;

import java.util.Arrays;

import com.abs.Factory;
import com.anotacion.MiComponente;
import com.impl.FactoryImpl;
import com.models.Laptop;
import com.models.Pc;
import com.models.Server;

public class Main {

	public static void main(String[] args) {
		Factory fac = new FactoryImpl();
		System.out.println(Laptop.class.getAnnotation(MiComponente.class).name());
		fac.create(Laptop.class.getAnnotation(MiComponente.class).name());
		fac.create(Pc.class.getAnnotation(MiComponente.class).name());
		fac.create(Server.class.getAnnotation(MiComponente.class).name());

	}

}
