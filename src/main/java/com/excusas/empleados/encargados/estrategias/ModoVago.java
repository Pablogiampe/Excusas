package com.excusas.empleados.encargados.estrategias;

import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;

public class ModoVago implements ModoResolucion {
    
    @Override
    public boolean debeEvaluar(Excusa excusa) {
        return false; // No evalúa, siempre pasa al siguiente
    }

    @Override
    public void ejecutarAccionAdicional(Excusa excusa, EmailSender emailSender) {

    }
}
