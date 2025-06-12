package com.excusas.empleados.encargados.modos;

import com.excusas.empleados.encargados.EncargadoBase;
import com.excusas.excusas.Excusa;

public class ModoNormal implements IModo {
    @Override
    public void manejar(EncargadoBase encargado, Excusa excusa) {
        encargado.procesar(excusa);
    }
}
