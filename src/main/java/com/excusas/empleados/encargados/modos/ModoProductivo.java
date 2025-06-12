package com.excusas.empleados.encargados.modos;

import com.excusas.empleados.encargados.EncargadoBase;
import com.excusas.excusas.Excusa;

public class ModoProductivo implements IModo {
    @Override
    public void manejar(EncargadoBase encargado, Excusa excusa) {
        boolean procesadaAntes = excusa.serManejadaPor(encargado);
        
        if (procesadaAntes) {
            encargado.getEmailSender().enviarEmail(
                "cto@excusas.com",
                encargado.getEmail(),
                "Notificación de excusa procesada",
                "Se ha procesado una excusa para el empleado: " + excusa.getEmpleado().getNombre() + 
                " por el encargado: " + encargado.getNombre()
            );
            
            System.out.println("LOG [Modo Productivo]: Se está procesando una excusa para el empleado: " +
                    excusa.getEmpleado().getNombre());
        } else {
            encargado.getSiguiente().manejarExcusa(excusa);
        }
    }
}
