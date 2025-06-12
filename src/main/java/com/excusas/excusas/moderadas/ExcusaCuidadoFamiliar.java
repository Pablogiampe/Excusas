package com.excusas.excusas.moderadas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.ManejadorExcusas;
import com.excusas.excusas.MotivoExcusa;

public class ExcusaCuidadoFamiliar extends ExcusaModerada {

    public ExcusaCuidadoFamiliar(Empleado empleado) {
        super(empleado, MotivoExcusa.CUIDADO_FAMILIAR);
    }

    @Override
    public boolean serManejadaPor(ManejadorExcusas manejador) {
        return manejador.manejar(this);
    }
}
