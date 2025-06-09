package com.excusas.excusas;

import com.excusas.empleados.Empleado;

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

    public abstract String getTipo();
}
