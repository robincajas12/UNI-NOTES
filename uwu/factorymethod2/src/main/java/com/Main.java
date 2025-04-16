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
		fac.init("com.models");
		fac.create(Laptop.class);
		fac.create(Pc.class);
		fac.create(Server.class);

	}

}
