package ec.edu.uce.inter;

public class Observador2 implements IObserver{
    @Override
    public void update(int i) {
        System.out.println("Observador 2  el valor ha sido moficado por "+ i);
    }
}
