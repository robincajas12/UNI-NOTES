package com.interfaces;

import java.lang.reflect.InvocationTargetException;

public interface Factory<T,U>{
    public void load(String path);
    public T get(String name, String parameterName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
