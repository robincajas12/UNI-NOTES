package com.adapter;

import com.anotation.Adaptable;
import com.inter.Vehicle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Adapter<T> implements Vehicle {
    private final T instance;
    private Method method;
    public Adapter(T instance)
    {
        this.instance = instance;
        Class<?> clazz = instance.getClass();
        if(clazz.isAnnotationPresent(Adaptable.class))
        {
            var anotation = clazz.getAnnotation(Adaptable.class);
            try {
                method = clazz.getMethod(anotation.metodo());
            }catch (NoSuchMethodException e)
            {
                e.printStackTrace();

            }

        }
        else throw new RuntimeException("The class is not adaptable " + instance.getClass().getName());
    }
    @Override
    public void drive() {
        if(method != null)
        {
            try {
                method.invoke(instance);
                return;
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

        }
        throw new RuntimeException("method is null");
    }
}
