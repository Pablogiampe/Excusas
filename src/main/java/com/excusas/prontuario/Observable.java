package com.excusas.prontuario;

public interface Observable {
    void agregarObserver(Observer observer);
    void quitarObserver(Observer observer);
    void notificarObservers(Prontuario prontuario);
}
