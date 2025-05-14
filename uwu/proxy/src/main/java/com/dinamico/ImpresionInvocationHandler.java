package com.dinamico;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ImpresionInvocationHandler implements InvocationHandler {
    private Object target;
    public ImpresionInvocationHandler(Object target)
    {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.printf("****** Operaciones %s(%s) \n", method.getName(), Arrays.toString(args));
        long inicio = System.nanoTime();
        Object res = method.invoke(target, args);
        long t = System.nanoTime() - inicio;
        System.out.printf("time taken: %.3f ms/n \n", t/1000.f);
        return res;
    }
}
