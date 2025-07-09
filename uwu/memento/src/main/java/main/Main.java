package main;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola Mundo!");
        CareTaker miCare = new CareTaker();
        Originator miOriginator = new Originator();
        miOriginator.setName("Lol");
        miOriginator.setName("LolX");
        miOriginator.setName("LolXD");
        miOriginator.setName("Lol123");
        miOriginator.setName("LolSSS");
       miOriginator.restaurarMemento(miOriginator.miCare.getMemento(0   ));
       System.out.println(miOriginator.getName());
    }


}
