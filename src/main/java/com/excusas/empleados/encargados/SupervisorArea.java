package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.modos.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;
import com.excusas.excusas.moderadas.ExcusaModerada; // Se importa la excusa específica
import com.excusas.mail.EmailSender;

public class SupervisorArea extends EncargadoBase {

    public SupervisorArea(String nombre, String email, int legajo,
                          ModoResolucion modoResolucion, EmailSender emailSender) {
        super(nombre, email, legajo, modoResolucion, emailSender);
    }

    // Se sobreescribe el método para manejar SOLO excusas moderadas
    // Esto reemplaza a los antiguos esResponsable() y procesarExcusa()
    @Override
    public void manejar(ExcusaModerada excusa) {
        System.out.println("Supervisor " + this.getNombre() + " procesando excusa MODERADA.");

        // Se ejecuta la acción adicional de la estrategia
        this.modoResolucion.ejecutarAccionAdicional(excusa);

        if (excusa.getMotivo() == MotivoExcusa.PERDIDA_SUMINISTRO) {
            emailSender.enviarEmail(
                    "EDESUR@mailfake.com.ar",
                    this.getEmail(),
                    "Consulta sobre corte de suministro",
                    "Consulta si hubo corte de suministro en la zona del empleado " + excusa.getEmpleado().getNombre()
            );
        }

        enviarNotificacion(excusa, excusa.getEmpleado().getEmail());
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