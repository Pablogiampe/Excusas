package com.excusas.empleados.encargados.estrategias;

import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;

public class ModoNormal implements ModoResolucion {
    
    @Override
    public boolean debeEvaluar(Excusa excusa) {
        return true;
    }

    @Override
    public void ejecutarAccionAdicional(Excusa excusa, EmailSender emailSender) {

    }
}
