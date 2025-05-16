package com.impl;

import com.dinamico.ImpresionInvocationHandler;
import com.inter.OperacionesNumeros;
import com.sun.tools.attach.AgentInitializationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public final class OperacionesFactory {
    static Map<Class<?>, Class<?>> servicio = new HashMap<>();
    public static void register(Class<?> inter, Class<?> impl)
    {
        servicio.put(inter, impl);
    }
    public static <T> T create(Class<T> inter) throws NoSuchMethodException {
        if(!servicio.containsKey(inter)) throw  new RuntimeException("No interface found the the hash map :(");

        Class<?> impl = servicio.get(inter);
        Constructor<?> constructor = impl.getDeclaredConstructor();
        try
        {
            Object target = constructor.newInstance();
            Object proxy = createProxy(target);
            return (T) proxy;

        }catch ( InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        throw new RuntimeException("Unexpected error");
    }
    private static Object createProxy(Object target)
    {
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new ImpresionInvocationHandler(target));
        return proxy;
    };
   /* public static <T> T createOperation(T  instance)
    {
        Object proxy =  OperacionesFactory.createProxy(instance);
        return (T)(proxy);
    }

    public static <T> T createOperationesCadenas(T  instance)
    {
        Object proxy =  createProxy(instance);
        return (T)(proxy);
    }*/
}
