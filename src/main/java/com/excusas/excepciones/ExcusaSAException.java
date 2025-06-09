package com.excusas.excepciones;

public class ExcusaSAException extends RuntimeException {
    
    public ExcusaSAException(String mensaje) {
        super(mensaje);
    }
    
    public ExcusaSAException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
