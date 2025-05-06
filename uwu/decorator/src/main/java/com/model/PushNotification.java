package com.model;

import com.inter.Notification;

public class PushNotification extends NotificationDecorator{
    public PushNotification(Notification notification) {
        super(notification);
    }
    private void sendPush(String msg)
    {
        System.out.println(msg + " PUSH");
    }
    @Override
    public void send(String msg)
    {
        super.send(msg);
        sendPush(msg);
    }
}
