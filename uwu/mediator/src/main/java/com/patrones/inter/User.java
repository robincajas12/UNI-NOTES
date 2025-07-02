package com.patrones.inter;

public abstract class User {
    protected String name;
    protected IMediator mediator;
    public abstract  void send(String msg);
    public abstract  void receive(String msg);
    public User(IMediator mediator,String name)
    {
        this.mediator = mediator;
        this.name = name;
    }
}
