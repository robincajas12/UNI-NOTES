package com.model;

import com.inter.Notification;

public class SMSNotification extends NotificationDecorator{
    public SMSNotification(Notification notification) {
        super(notification);

    }
    public void sendSMS(String msg)
    {
        System.out.println(msg + " SMS");
    }
    @Override
    public void send(String msg)
    {
        super.send(msg);
        sendSMS(msg);
    }
}
