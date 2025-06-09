package com.excusas.prontuario;

import com.excusas.empleados.Empleado;
import com.excusas.excusas.Excusa;

public class Prontuario {
    private Empleado empleado;
    private Excusa excusa;
    private int numeroLegajo;

    public Prontuario(Empleado empleado, Excusa excusa, int numeroLegajo) {
        this.empleado = empleado;
        this.excusa = excusa;
        this.numeroLegajo = numeroLegajo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Excusa getExcusa() {
        return excusa;
    }

    public int getNumeroLegajo() {
        return numeroLegajo;
    }

    @Override
    public String toString() {
        return "Prontuario{" +
                "empleado=" + empleado +
                ", excusa=" + excusa.getTipo() +
                ", numeroLegajo=" + numeroLegajo +
                '}';
    }
}
