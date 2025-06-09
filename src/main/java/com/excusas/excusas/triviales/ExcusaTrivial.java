package com.excusas.excusas.triviales;

import com.excusas.empleados.Empleado;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;

public class ExcusaTrivial extends Excusa {
    
    public ExcusaTrivial(Empleado empleado, MotivoExcusa motivo) {
        super(empleado, motivo);
        if (motivo != MotivoExcusa.QUEDARSE_DORMIDO && motivo != MotivoExcusa.PERDI_TRANSPORTE) {
            throw new IllegalArgumentException("Motivo no válido para excusa trivial");
        }
    }

    @Override
    public String getTipo() {
        return "TRIVIAL";
    }
}
