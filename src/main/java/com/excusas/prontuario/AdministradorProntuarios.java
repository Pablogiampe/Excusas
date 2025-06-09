package com.excusas.prontuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.excusas.excepciones.ErrorProcesamiento;

public class AdministradorProntuarios implements Observable {
    private static AdministradorProntuarios instancia;
    private List<Prontuario> prontuarios;
    private List<Observer> observers;

    private AdministradorProntuarios() {
        this.prontuarios = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public static synchronized AdministradorProntuarios getInstance() {
        if (instancia == null) {
            instancia = new AdministradorProntuarios();
        }
        return instancia;
    }

    public void guardarProntuario(Prontuario prontuario) {
        if (prontuario == null) {
            throw new ErrorProcesamiento("No se puede guardar un prontuario nulo");
        }
    
        if (prontuario.getEmpleado() == null) {
            throw new ErrorProcesamiento("El prontuario debe tener un empleado asociado");
        }
    
        if (prontuario.getExcusa() == null) {
            throw new ErrorProcesamiento("El prontuario debe tener una excusa asociada");
        }
    
        try {
            prontuarios.add(prontuario);
            notificarObservers(prontuario);
        } catch (Exception e) {
            throw new ErrorProcesamiento("Error al guardar el prontuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void agregarObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void quitarObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notificarObservers(Prontuario prontuario) {
        for (Observer observer : new ArrayList<>(observers)) {
            observer.notificar(prontuario);
        }
    }

    public List<Prontuario> getProntuarios() {
        return new ArrayList<>(prontuarios);
    }
    
    // Método para tests
    public List<Observer> getObservers() {
        return Collections.unmodifiableList(observers);
    }
    

    public static void reset() {
        if (instancia != null) {
            instancia.prontuarios.clear();
            instancia.observers.clear();
        }
    }
}
