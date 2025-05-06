package com.base;

import com.inter.Notification;

public class BaseNotification implements Notification {

    @Override
    public void send(String msg) {
        System.out.println(msg +" base");
    }
}
