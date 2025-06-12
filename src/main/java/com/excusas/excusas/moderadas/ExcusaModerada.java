package com.excusas.excusas.moderadas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.ManejadorExcusas;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;

public class ExcusaModerada extends Excusa {

    public ExcusaModerada(Empleado empleado, MotivoExcusa motivo) {
        super(empleado, motivo);
        if (motivo != MotivoExcusa.PERDIDA_SUMINISTRO && motivo != MotivoExcusa.CUIDADO_FAMILIAR) {
            throw new IllegalArgumentException("Motivo no válido para excusa moderada");
        }
    }

    @Override
    public boolean serManejadaPor(ManejadorExcusas manejador) {
        return manejador.manejar(this);
    }
}
