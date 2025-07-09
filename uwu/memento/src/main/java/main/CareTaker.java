package main;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<Memento> estados;
    public CareTaker()
    {
        this.estados = new ArrayList<>();
    }
    public void addMementos(Memento m)
    {
        estados.add(m);
    }
    public Memento getMemento(int index)
    {
        if(index < 0) throw new RuntimeException("XD no negative numbers");
        if(index >= estados.size())  throw new RuntimeException("index too big");
        return estados.get(index);
    }
}
