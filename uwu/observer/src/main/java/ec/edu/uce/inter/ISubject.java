package ec.edu.uce.inter;

public interface ISubject {
    void register(IObserver observer);
    void unregister(IObserver observer);
    void notifyObservers(int valor);
}
