package com.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.abs.Factory;
import com.anotacion.MiComponente;
import com.google.common.reflect.ClassPath;
import com.models.Laptop;
import com.models.Pc;
import com.models.Server;

public class FactoryImpl implements Factory{
	private Map<String, Class> componentes = new HashMap<String, Class>();
	@Override
	public void init(String packageName) {
		System.out.println("inicialization"+ FactoryImpl.class.getClassLoader());
		try {
			ClassPath classPath = ClassPath.from(FactoryImpl.class.getClassLoader());
			var classes = classPath.getTopLevelClassesRecursive(packageName);
			for (var myClass : classes) {
				if(!myClass.load().isAnnotationPresent(MiComponente.class)) continue;
				String anotation = myClass.load().getAnnotation(MiComponente.class).name();
				componentes.put(anotation, myClass.load());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T> T create(Class<T> clazz) {
		if(!clazz.isAnnotationPresent(MiComponente.class))  throw new RuntimeException(clazz.getName() +" must implement " + MiComponente.class.getName());
		if(!componentes.containsKey(clazz.getAnnotation(MiComponente.class).name())) throw new RuntimeException(MiComponente.class +" not found");
		try {
			var constructor = componentes.get(clazz.getAnnotation(MiComponente.class).name()).getConstructor();
			return (T) constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
