package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class AppChain {
    public static void main(String[] args) {
        new ArrayList<String>(List.of(new String[]{"xd", "xd xd xd", "hola"}))
                .stream().map(t-> t.split("")).filter(t->t.length <= 4).forEach(t->{
                    List.of(t).forEach(System.out::println);
                });


        Handler d = new DirectorHandler();
        Handler m = new ManagerHandler();
        Handler c = new CEOHandler();
        m.setNext(d);
        d.setNext(c);
        List<Request> peteciones = new ArrayList<>();
        for(int i = 0; i <= 5; i++)
        {
            peteciones.add(new Request(5000 + 2000*new Random().nextInt(10), "lavado de dinero"));
        }
        peteciones.forEach(m::handleRequest);

    }
}