package com.patrones.impl;

import com.patrones.inter.IMediator;
import com.patrones.inter.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chat implements IMediator {
    List<User> users = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    @Override
    public void sendMessage(String msg, User user) {
        lock.lock();
        try {
            for (var u : users)
            {
                if(u != user)
                {
                    u.receive(msg);
                }
            }

        }finally {
            lock.unlock();
        }
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }
}
