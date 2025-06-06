package ec.edu.uce.inter;

import java.util.ArrayList;
import java.util.List;

public class Subject1 implements ISubject{
    private List<IObserver> suscribers = new ArrayList<>();

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
        notifyObservers(valor);
    }

    int valor = 5;
    @Override
    public void register(IObserver observer) {
        suscribers.add(observer);
    }

    @Override
    public void unregister(IObserver observer) {
        suscribers.remove(observer);
    }

    @Override
    public void notifyObservers(int valor) {
        for(var item : suscribers)
        {
            item.update(valor);
        }
    }
}
