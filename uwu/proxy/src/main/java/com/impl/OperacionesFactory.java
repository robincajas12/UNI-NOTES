package com.impl;

import com.dinamico.ImpresionInvocationHandler;
import com.inter.OperacionesNumeros;

import java.lang.reflect.Proxy;
import java.util.Map;

public final class OperacionesFactory {
    Map<String, Class<?>> proxys;
    private static Object createProxy(Object target)
    {
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new ImpresionInvocationHandler(target));
        return proxy;
    };
    public static <T> T createOperation(T  instance)
    {
        Object proxy =  OperacionesFactory.createProxy(instance);
        return (T)(proxy);
    }

    public static <T> T createOperationesCadenas(T  instance)
    {
        Object proxy =  OperacionesFactory.createProxy(instance);
        return (T)(proxy);
    }
}
