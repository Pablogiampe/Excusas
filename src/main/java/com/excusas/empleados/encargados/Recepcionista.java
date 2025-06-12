package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.modos.IModo;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.triviales.ExcusaTrivial;
import com.excusas.mail.EmailSender;

public class Recepcionista extends EncargadoBase {

    public Recepcionista(String nombre, String email, int legajo,
                         IModo modo, EmailSender emailSender) {
        super(nombre, email, legajo, modo, emailSender);
    }

    @Override
    public boolean manejar(ExcusaTrivial excusa) {
        System.out.println("Recepcionista " + this.getNombre() + " procesando excusa TRIVIAL.");
        enviarNotificacion(excusa, excusa.getEmpleado().getEmail());
        return true;
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
