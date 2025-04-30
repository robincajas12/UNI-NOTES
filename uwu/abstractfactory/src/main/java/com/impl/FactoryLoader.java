package com.impl;

import com.anotation.Theme;
import com.google.common.reflect.ClassPath;
import com.inter.UIFactory;
import org.checkerframework.checker.guieffect.qual.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class FactoryLoader {
    Map<String, Class<? extends UIFactory>> map = new HashMap<>();
    public void load(String pakageName)
    {
        try{
            ClassPath path = ClassPath.from(getClass().getClassLoader());
            for (ClassPath.ClassInfo classInfo : path.getTopLevelClassesRecursive(pakageName))
            {
                Class<?> clazz = classInfo.load();
                if(UIFactory.class.isAssignableFrom(clazz))
                {
                    Theme annotation = clazz.getAnnotation(Theme.class);
                    if(clazz.isAnnotationPresent(Theme.class))
                    {
                        System.out.println(annotation.value());
                        map.put(annotation.value(), (Class<? extends UIFactory>) clazz);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading the factories");
        }
    }
    public UIFactory create(String theme) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends UIFactory> factory = map.get(theme);
        if(factory == null) throw new RuntimeException("factory with the theme " + theme + " assigned not found");
        Constructor<? extends UIFactory> constructor =  factory.getConstructor();
        return constructor.newInstance();
    }
}
