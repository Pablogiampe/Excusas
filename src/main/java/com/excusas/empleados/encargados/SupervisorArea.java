package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.estrategias.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;
import com.excusas.mail.EmailSender;

public class SupervisorArea extends EncargadoBase {

    public SupervisorArea(String nombre, String email, int legajo, 
                         ModoResolucion modoResolucion, EmailSender emailSender) {
        super(nombre, email, legajo, modoResolucion, emailSender);
    }

    @Override
    protected boolean puedeManear(Excusa excusa) {
        return "MODERADA".equals(excusa.getTipo());
    }

    @Override
    protected void procesarExcusa(Excusa excusa) {
        if (excusa.getMotivo() == MotivoExcusa.PERDIDA_SUMINISTRO) {
            emailSender.enviarEmail(
                "EDESUR@mailfake.com.ar",
                this.getEmail(),
                "Consulta sobre corte de suministro",
                "Consulta si hubo corte de suministro en la zona del empleado " + 
                excusa.getEmpleado().getNombre()
            );
        } else if (excusa.getMotivo() == MotivoExcusa.CUIDADO_FAMILIAR) {
            emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(),
                this.getEmail(),
                "Consulta sobre situación familiar",
                "¿Está todo bien con su situación familiar?"
            );
        }
    }
}
