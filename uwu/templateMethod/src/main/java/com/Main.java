package com;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");
        Cajero miCajero = new Cajero("CTA. 123", 100, 1);

        while (true)
        {
            int option = menu();
            miCajero.procesar("xdd", 8,option);
        }

    }
    public static int menu()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Escoja una opción");
        System.out.println("1 depositar");
        System.out.println("2 retirar");
        return sc.nextInt();
    }
}