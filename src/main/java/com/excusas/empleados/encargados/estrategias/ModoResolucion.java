package com.excusas.empleados.encargados.estrategias;

import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;

public interface ModoResolucion {
    boolean debeEvaluar(Excusa excusa);
    void ejecutarAccionAdicional(Excusa excusa, EmailSender emailSender);
}
