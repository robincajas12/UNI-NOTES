package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CEOHandler implements Handler<Request>{
    private Handler<Request> nextHandler;

    @Override
    public void handleRequest(Request request) {
        if(request.getMonto() >= 10000)
        {
            System.out.println("El "+ this.getClass().getSimpleName() + " ha respondido lka solicutuyd con el monto :" + request.getMonto() + "con el motivo asociado " + request.getDescripcion());
        }

    }

    @Override
    public void setNext(Handler<Request> handler) {
        this.nextHandler = handler;
    }
}
