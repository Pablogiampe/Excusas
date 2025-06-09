package com.excusas.excusas.inverosimiles;

import com.excusas.empleados.Empleado;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;

public class ExcusaInverosimil extends Excusa {
    
    public ExcusaInverosimil(Empleado empleado, MotivoExcusa motivo) {
        super(empleado, motivo);
        if (motivo != MotivoExcusa.INCREIBLE_INVEROSIMIL) {
            throw new IllegalArgumentException("Motivo no válido para excusa inverosímil");
        }
    }

    @Override
    public String getTipo() {
        return "INVEROSIMIL";
    }
}
