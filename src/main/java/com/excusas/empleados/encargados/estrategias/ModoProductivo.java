package com.excusas.empleados.encargados.estrategias;

import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;

public class ModoProductivo implements ModoResolucion {
    private static final String CTO_EMAIL = "cto@excusas.com";
    
    @Override
    public boolean debeEvaluar(Excusa excusa) {
        return true;
    }

    @Override
    public void ejecutarAccionAdicional(Excusa excusa, EmailSender emailSender) {
        emailSender.enviarEmail(
            CTO_EMAIL,
            "sistema@excusas.com",
            "Notificación de Procesamiento",
            "Se está procesando una excusa en modo productivo para el empleado: " + 
            excusa.getEmpleado().getNombre()
        );
    }
}
