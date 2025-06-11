package com.excusas.excusas.inverosimiles;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.ManejadorExcusas;
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
    public void serManejadaPor(ManejadorExcusas manejador) {
        manejador.manejar(this); // Llama a manejar(ExcusaInverosimil excusa)
    }
}