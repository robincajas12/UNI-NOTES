package com;

import com.impl.Desarrollador;
import com.impl.Tester;
import com.interfaces.IEmpleado;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class EmpleadoFactory {
    private static HashMap<String, Class<? extends IEmpleado>> map = new HashMap<>();
    public EmpleadoFactory()
    {
        map.put("Dev", Desarrollador.class);
        map.put("Tester", Tester.class);
    }
    public IEmpleado getIntance(String tipo) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!map.containsKey(tipo)) throw  new RuntimeException("Error " + tipo + "class donest exist in the hashMap");
        return map.get(tipo).getConstructor().newInstance();
    }
}
