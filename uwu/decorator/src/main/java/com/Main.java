package com;

import com.base.BaseNotification;
import com.guava.Factory;
import com.inter.Notification;
import com.model.PushNotification;
import com.model.SMSNotification;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //Notification notification = new SMSNotification(new PushNotification(new BaseNotification()));
        //notification.send("Hola");
        Factory f = new Factory();
        f.load("com.model");
        Notification not = f.get(SMSNotification.class, f.get(PushNotification.class, new BaseNotification()));
        not.send("Hola");
    }
}
