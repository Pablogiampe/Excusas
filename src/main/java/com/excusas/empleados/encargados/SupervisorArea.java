package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.modos.IModo;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.moderadas.ExcusaPerdidaSuministro;
import com.excusas.excusas.moderadas.ExcusaCuidadoFamiliar;
import com.excusas.mail.EmailSender;

public class SupervisorArea extends EncargadoBase {

    public SupervisorArea(String nombre, String email, int legajo,
                          IModo modo, EmailSender emailSender) {
        super(nombre, email, legajo, modo, emailSender);
    }

    @Override
    public boolean manejar(ExcusaPerdidaSuministro excusa) {
        System.out.println("Supervisor " + this.getNombre() + " procesando excusa de PERDIDA DE SUMINISTRO.");

        emailSender.enviarEmail(
                "EDESUR@mailfake.com.ar",
                this.getEmail(),
                "Consulta sobre corte de suministro",
                "Consulta si hubo corte de suministro en la zona del empleado " + excusa.getEmpleado().getNombre()
        );

        enviarNotificacion(excusa, excusa.getEmpleado().getEmail());
        return true;
    }

    @Override
    public boolean manejar(ExcusaCuidadoFamiliar excusa) {
        System.out.println("Supervisor " + this.getNombre() + " procesando excusa de CUIDADO FAMILIAR.");

        emailSender.enviarEmail(
                "rrhh@excusas.com",
                this.getEmail(),
                "Verificación de situación familiar",
                "Verificar la situación familiar del empleado " + excusa.getEmpleado().getNombre() +
                " para validar la excusa de cuidado familiar."
        );

        enviarNotificacion(excusa, excusa.getEmpleado().getEmail());
        return true;
    }

    @Override
    protected String asunto(Excusa excusa) {
        return "Excusa moderada en revisión";
    }

    @Override
    protected String cuerpo(Excusa excusa) {
        return "Estimado/a " + excusa.getEmpleado().getNombre() + ",\n\nHemos recibido su excusa y la estamos verificando. Nos pondremos en contacto a la brevedad.\n\nSaludos.";
    }
}
