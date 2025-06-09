package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.estrategias.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;

public class Recepcionista extends EncargadoBase {

    public Recepcionista(String nombre, String email, int legajo, 
                        ModoResolucion modoResolucion, EmailSender emailSender) {
        super(nombre, email, legajo, modoResolucion, emailSender);
    }

    @Override
    protected boolean puedeManear(Excusa excusa) {
        return "TRIVIAL".equals(excusa.getTipo());
    }

    @Override
    protected void procesarExcusa(Excusa excusa) {
        emailSender.enviarEmail(
            excusa.getEmpleado().getEmail(),
            this.getEmail(),
            "motivo demora",
            "la licencia fue aceptada"
        );
    }
}
