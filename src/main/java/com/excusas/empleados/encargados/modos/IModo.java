package com.excusas.empleados.encargados.modos;

import com.excusas.empleados.encargados.EncargadoBase;
import com.excusas.excusas.Excusa;

public interface IModo {
    void manejar(EncargadoBase encargado, Excusa excusa);
}
