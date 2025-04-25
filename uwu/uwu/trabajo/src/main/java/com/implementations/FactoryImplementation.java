package com.implementations;

import com.abs.Factory;
import com.anotations.FactoryAnnotation;
import com.google.common.reflect.ClassPath;

import java.util.HashMap;
import java.util.Map;

public class FactoryImplementation  implements Factory {
    private Map<String, Class> componentes = new HashMap<String, Class>();
    @Override
    public void init(String packageName) {
        System.out.println("inicialization"+ FactoryImplementation.class.getClassLoader());
        try {
            ClassPath classPath = ClassPath.from(FactoryImplementation.class.getClassLoader());
            var classes = classPath.getTopLevelClassesRecursive(packageName);
            for (var myClass : classes) {
                if(!myClass.load().isAnnotationPresent(FactoryAnnotation.class)) continue;
                String anotation = myClass.load().getAnnotation(FactoryAnnotation.class).name();
                componentes.put(anotation, myClass.load());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T create(Class<T> clazz) {
        System.out.println("xddd");
        if(!clazz.isAnnotationPresent(FactoryAnnotation.class))  throw new RuntimeException(clazz.getName() +" must implement " + FactoryAnnotation.class.getName());
        if(!componentes.containsKey(clazz.getAnnotation(FactoryAnnotation.class).name())) throw new RuntimeException(FactoryAnnotation.class +" not found");
        try {
            var constructor = componentes.get(clazz.getAnnotation(FactoryAnnotation.class).name()).getConstructor();
            return (T) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}