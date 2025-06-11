package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.modos.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;

public class Recepcionista extends EncargadoBase {

    public Recepcionista(String nombre, String email, int legajo,
                         ModoResolucion modoResolucion, EmailSender emailSender) {
        super(nombre, email, legajo, modoResolucion, emailSender);
    }

    @Override
    protected boolean esResponsable(Excusa excusa) {
        return "TRIVIAL".equals(excusa.getTipo());
    }

    @Override
    protected void procesarExcusa(Excusa excusa) {
        System.out.println("Recepcionista " + this.getNombre() + " procesando excusa TRIVIAL.");
        enviarNotificacion(excusa, excusa.getEmpleado().getEmail());
    }

    @Override
    protected String asunto(Excusa excusa) {
        return "Notificación de excusa aceptada";
    }

    @Override
    protected String cuerpo(Excusa excusa) {
        return "Estimado/a " + excusa.getEmpleado().getNombre() + ",\n\nSu excusa ha sido procesada y aceptada.\n\nSaludos cordiales.";
    }
}