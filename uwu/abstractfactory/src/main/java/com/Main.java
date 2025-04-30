package com;

import com.impl.FactoryImpClaro;
import com.impl.FactoryImplObcuro;
import com.impl.FactoryLoader;
import com.inter.Boton;
import com.inter.UIFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


public class Main {

	public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

		ConfigLoader cg = new ConfigLoader("/config.properties");
		FactoryLoader miFactoryLoader = new FactoryLoader();
		miFactoryLoader.load("com.impl");
		UIFactory miFactory = null;
		miFactory = miFactoryLoader.create(cg.p.getProperty("theme"));
		miFactory.crearBoton().dibujar();

    }

}
