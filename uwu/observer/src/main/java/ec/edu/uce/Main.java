package ec.edu.uce;

import ec.edu.uce.inter.Observador1;
import ec.edu.uce.inter.Observador2;
import ec.edu.uce.inter.Subject1;

import java.util.Optional;

public class Main {
    public static void main(String[] args){
        Subject1 a = new Subject1();
        a.register(new Observador1());
        a.register(new Observador2());
        a.setValor(55);
        a.setValor(87);
        a.setValor(79);
        Optional<String> xd = Optional.of("String");
    }
}