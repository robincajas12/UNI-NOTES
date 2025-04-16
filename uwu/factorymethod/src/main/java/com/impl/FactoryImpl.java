package com.impl;

import com.abs.Factory;
import com.anotacion.MiComponente;
import com.models.Laptop;
import com.models.Pc;
import com.models.Server;

public class FactoryImpl implements Factory{

	@Override
	public <T> T create(String name) {
		if(name.equals(Pc.class.getAnnotation(MiComponente.class).name())) return (T) new Pc();
		if(name.equals(Laptop.class.getAnnotation(MiComponente.class).name())) return (T) new Laptop();
		if(name.equals(Server.class.getAnnotation(MiComponente.class).name())) return (T) new Server();
		throw new IllegalArgumentException();
	}
}
