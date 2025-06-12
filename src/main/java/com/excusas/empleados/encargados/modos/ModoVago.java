package com.excusas.empleados.encargados.modos;

import com.excusas.empleados.encargados.EncargadoBase;
import com.excusas.excusas.Excusa;

public class ModoVago implements IModo {
    @Override
    public void manejar(EncargadoBase encargado, Excusa excusa) {
        System.out.println("LOG [Modo Vago]: El encargado " + encargado.getNombre() + 
                " está en modo vago y pasa la excusa sin procesarla.");
        
        encargado.getSiguiente().manejarExcusa(excusa);
    }
}
