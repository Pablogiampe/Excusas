package com.excusas.empleados.encargados;

import com.excusas.excepciones.ErrorConfiguracion;
import com.excusas.excusas.Excusa;

public class LineaEncargados {
    private ManejadorExcusas primerEncargado;

    public void crearCadena(EncargadoBase... encargados) {
        if (encargados.length == 0) {
            throw new ErrorConfiguracion("La cadena de encargados no puede estar vacía");
        }

        for (int i = 0; i < encargados.length; i++) {
            if (encargados[i] == null) {
                throw new ErrorConfiguracion("El encargado en la posición " + i + " no puede ser nulo");
            }
        }

        primerEncargado = encargados[0];

        for (int i = 0; i < encargados.length - 1; i++) {
            encargados[i].setSiguiente(encargados[i + 1]);
        }

        encargados[encargados.length - 1].setSiguiente(new Rechazador());
    }

    public void manejarExcusa(Excusa excusa) {
        if (primerEncargado == null) {
            throw new ErrorConfiguracion("La cadena de encargados no ha sido inicializada");
        }
        primerEncargado.manejarExcusa(excusa);
    }


}