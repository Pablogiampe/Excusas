package com.excusas.empleados.encargados.modos;

import com.excusas.excusas.Excusa;


public class ModoProductivo implements ModoResolucion {
    @Override
    public void ejecutarAccionAdicional(Excusa excusa) {
        System.out.println("LOG [Modo Productivo]: Se está procesando una excusa para el empleado: " +
                excusa.getEmpleado().getNombre());
    }
}