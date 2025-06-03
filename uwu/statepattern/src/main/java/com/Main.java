package com;

import com.inter.State;
import com.model.Tamagochi;

import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        State.stateChange.subscribe(System.out::println);
        Tamagochi t = new Tamagochi();
        Scanner miSc = new Scanner(System.in);

        while (true)
        {
            Arrays.stream(new String[]{
                    "Que quieres hacer con tu tamagochi",
                    "1 alimentar",
                    "2 jugar",
                    "3 domir",
                    "0 para salir"

            }).forEach(System.out::println);
            int op = miSc.nextInt();
            if(op == 0 ) break;
            switch (op)
            {
                case 1: t.alimentar(); break;
                case 2: t.jugar(); break;
                case 3: t.zzzzzz(); break;

            }

        }

    }
}