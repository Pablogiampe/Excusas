package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.estrategias.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;
import com.excusas.prontuario.AdministradorProntuarios;
import com.excusas.prontuario.Observer;
import com.excusas.prontuario.Prontuario;

public class CEO extends EncargadoBase implements Observer {

    public CEO(String nombre, String email, int legajo, 
              ModoResolucion modoResolucion, EmailSender emailSender) {
        super(nombre, email, legajo, modoResolucion, emailSender);
        AdministradorProntuarios.getInstance().agregarObserver(this);
    }

    @Override
    protected boolean puedeManear(Excusa excusa) {
        return "INVEROSIMIL".equals(excusa.getTipo());
    }

    @Override
    protected void procesarExcusa(Excusa excusa) {

        emailSender.enviarEmail(
            excusa.getEmpleado().getEmail(),
            this.getEmail(),
            "Excusa Aprobada",
            "Aprobado por creatividad"
        );


        Prontuario prontuario = new Prontuario(
            excusa.getEmpleado(),
            excusa,
            excusa.getEmpleado().getLegajo()
        );
        
        AdministradorProntuarios.getInstance().guardarProntuario(prontuario);
    }

    @Override
    public void notificar(Prontuario prontuario) {

        System.out.println("CEO " + this.getNombre() + " notificado sobre nuevo prontuario: " + 
                          prontuario);
    }
}
