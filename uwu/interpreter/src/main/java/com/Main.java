package com;

import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Numero n1 = new Numero(5);
        Numero n2 = new Numero(8);
        Variable x = new Variable("x");
        Variable y = new Variable("x");
        Variable z = new Variable("x");
        Map<String, Integer> map = new HashMap<>();
        map.put("x", 3);
        map.put("y", 5);
        map.put("z", 5);
        map.put("a", 5);

        Expresiones miSuma = new Suma(new Variable("x"), n1);
        Expresiones multiply = new Multitplicacion(miSuma, new Variable("y"));
        
        System.out.println(multiply.interprete(map));

    }
}