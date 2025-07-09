package main;

public class Originator {
    CareTaker miCare = new CareTaker();
    private String name;
    public void salvarMemento()
    {
        System.out.println("Se ha lanzado un mementto");
        miCare.addMementos(new Memento(name));
    }
    public CareTaker careTaker()
    {
        return miCare;
    }
    public String getName() {
        return name;
    }
    public void restaurarMemento(Memento m)
    {
        this.name = m.getStado();
    }
    public void setName(String name) {
        this.name = name;
        salvarMemento();
    }
}
