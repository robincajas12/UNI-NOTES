package com;

import com.inter.BasicCar;
import com.inter.Ford;
import com.inter.Toyota;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("Hola");
        BasicCar toyota = new Toyota("toyota");
        toyota.price = 500;

        BasicCar ford = new Ford("Ford");
        ford.price = 700;
        BasicCar cloneForFord = ford.clone();
        BasicCar cloneForToyota = toyota.clone();
        System.out.println(toyota);
        System.out.println(cloneForToyota);
        System.out.println(ford);
        System.out.println(cloneForFord);
    }
}
