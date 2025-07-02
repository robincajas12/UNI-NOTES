package com.patrones.impl;

import com.patrones.inter.IMediator;
import com.patrones.inter.User;

public class ConcreteUser extends User {
    public ConcreteUser(IMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void send(String msg) {
        System.out.println(super.name + " esta enviando el mensaje : " + msg);
        super.mediator.sendMessage(msg, this);
    }

    @Override
    public void receive(String msg) {
        System.out.println(super.name + " receive message: " + msg);
    }
}
