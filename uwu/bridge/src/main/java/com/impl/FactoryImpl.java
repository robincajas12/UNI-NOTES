package com.impl;

import com.anotation.AColor;
import com.anotation.AFigure;
import com.google.common.reflect.ClassPath;
import com.interfaces.Factory;
import com.interfaces.IColor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class FactoryImpl<T,U> implements Factory<T,U> {
    Map<String, Class<?>> figures = new HashMap();
    Map<String, Class<?>> colors = new HashMap<>();

    @Override
    public void load(String path) {
        try{
            ClassPath classPath = ClassPath.from(FactoryImpl.class.getClassLoader());
            var topLevelClasses = classPath.getTopLevelClassesRecursive(path);
            for(var topLevelClass : topLevelClasses)
            {
                var clazz = topLevelClass.load();
                if(clazz.isAnnotationPresent(AFigure.class))
                {
                    System.out.println(clazz.getAnnotation(AFigure.class).name());
                    figures.put(clazz.getAnnotation(AFigure.class).name(), clazz);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            ClassPath classPath = ClassPath.from(FactoryImpl.class.getClassLoader());
            var topLevelClasses = classPath.getTopLevelClassesRecursive(path);
            for(var topLevelClass : topLevelClasses)
            {
                var clazz = topLevelClass.load();
                if(clazz.isAnnotationPresent(AColor.class))
                {
                    colors.put(clazz.getAnnotation(AColor.class).name(), clazz);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public T get(String figure, String color) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var fig = figures.get(figure);
        var clr = colors.get(color);
        if(fig == null) throw new RuntimeException("figure with tha name donest exit");
        if(fig == null) throw new RuntimeException("color with tha name donest exit");
        return (T) fig.getConstructor(clr.getClass()).newInstance(clr.getConstructor(int.class).newInstance(0));
    }
}
