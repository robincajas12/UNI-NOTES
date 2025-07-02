package com.patrones.inter;

public interface IMediator {
    void sendMessage(String msg, User user);
    void addUser(User user);
}
