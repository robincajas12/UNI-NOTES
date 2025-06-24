package com;

public interface Handler<T> {
    public void handleRequest(T request);
    void setNext(Handler<T> handler);
}
