package com.excusas.excusas.complejas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.ManejadorExcusas;
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
    public void serManejadaPor(ManejadorExcusas manejador) {
        manejador.manejar(this); // Llama a manejar(ExcusaCompleja excusa)
    }
}