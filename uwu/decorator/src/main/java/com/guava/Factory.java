package com.guava;

import com.base.BaseNotification;
import com.google.common.reflect.ClassPath;
import com.inter.Notification;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Factory {
    Map<String, Class<? extends Notification>> clazzes = new HashMap<>();
    public void load(String packageName) throws IOException {
        ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
        System.out.println(classPath.toString());
        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(packageName)) {
            Class<?> loadedClass = classInfo.load(); // <--- Load the real class
            System.out.println(loadedClass.getName());
            if (Notification.class.isAssignableFrom(loadedClass)
                    && !loadedClass.isInterface()
                    && !java.lang.reflect.Modifier.isAbstract(loadedClass.getModifiers())) {

                Class<? extends Notification> castedClass = (Class<? extends Notification>) loadedClass;
                clazzes.put(classInfo.getName(), castedClass);
            }
        }
    }
    public <T> T get(Class<? extends Notification> clazz, Notification notification) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        var miCls = clazzes.get(clazz.getName());
        if(miCls == null) return null;
        return (T) miCls.getConstructor(Notification.class).newInstance(notification);
    }

}
