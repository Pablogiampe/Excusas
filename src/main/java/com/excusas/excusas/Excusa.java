package com.excusas.excusas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.ManejadorExcusas;

public abstract class Excusa {
    protected Empleado empleado;
    protected MotivoExcusa motivo;

    public Excusa(Empleado empleado, MotivoExcusa motivo) {
        this.empleado = empleado;
        this.motivo = motivo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public MotivoExcusa getMotivo() {
        return motivo;
    }

    // Método que retorna true si el manejador procesó la excusa
    public abstract boolean serManejadaPor(ManejadorExcusas manejador);
}
