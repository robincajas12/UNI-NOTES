package ec.edu.uce.inter;

public class Observador1 implements IObserver{
    @Override
    public void update(int i) {
        System.out.println("Observador 1 el valor ha sido moficado por "+ i);
    }
}
