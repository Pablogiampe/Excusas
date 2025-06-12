package com.excusas.empleados;

import com.excusas.empleados.encargados.LineaEncargados;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;


public interface IEmpleado {
    String getNombre();
    String getEmail();
    int getLegajo();


    Excusa generarYEnviarExcusa(MotivoExcusa motivo, LineaEncargados linea);
}
