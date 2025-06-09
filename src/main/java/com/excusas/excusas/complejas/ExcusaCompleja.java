package com.excusas.excusas.complejas;

import com.excusas.empleados.Empleado;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;

public class ExcusaCompleja extends Excusa {
    
    public ExcusaCompleja(Empleado empleado, MotivoExcusa motivo) {
        super(empleado, motivo);
        if (motivo != MotivoExcusa.IRRELEVANTE) {
            throw new IllegalArgumentException("Motivo no válido para excusa compleja");
        }
    }

    @Override
    public String getTipo() {
        return "COMPLEJA";
    }
}
