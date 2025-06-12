package com.excusas.excusas.moderadas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.ManejadorExcusas;
import com.excusas.excusas.MotivoExcusa;

public class ExcusaPerdidaSuministro extends ExcusaModerada {

    public ExcusaPerdidaSuministro(Empleado empleado) {
        super(empleado, MotivoExcusa.PERDIDA_SUMINISTRO);
    }

    @Override
    public boolean serManejadaPor(ManejadorExcusas manejador) {
        return manejador.manejar(this);
    }
}
