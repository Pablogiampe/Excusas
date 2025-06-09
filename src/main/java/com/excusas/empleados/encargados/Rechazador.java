package com.excusas.empleados.encargados;

import com.excusas.excusas.Excusa;

public class Rechazador implements ManejadorExcusas {
    
    @Override
    public void manejarExcusa(Excusa excusa) {
        System.out.println("Excusa rechazada: necesitamos pruebas contundentes");
    }
}
