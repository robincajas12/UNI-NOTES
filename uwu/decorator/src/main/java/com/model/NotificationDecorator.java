package com.model;

import com.inter.Notification;

public abstract class NotificationDecorator implements Notification {
    protected Notification notification;
    public NotificationDecorator(Notification notification)
    {
        this.notification = notification;
    }
    @Override
    public void send(String msg) {

        notification.send(msg);
    }
}
