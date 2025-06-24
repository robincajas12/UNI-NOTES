package com;

public class DirectorHandler implements Handler<Request> {
    private Handler<Request> nextHandler;
    @Override
    public void handleRequest(Request request) {
        if(request.getMonto() > 5000 && request.getMonto() <= 10000)
        {
            System.out.println("El "+ this.getClass().getSimpleName() + " ha respondido lka solicutuyd con el monto :" + request.getMonto() + "con el motivo asociado " + request.getDescripcion());
        }else if(this.nextHandler != null)
        {
            nextHandler.handleRequest(request);
        }

    }

    @Override
    public void setNext(Handler<Request> handler) {
        this.nextHandler = handler;
    }
}
