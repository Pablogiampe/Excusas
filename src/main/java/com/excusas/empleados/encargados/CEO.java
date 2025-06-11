package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.modos.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil; // Importar la excusa específica
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
    public void manejar(ExcusaInverosimil excusa) {
        System.out.println("CEO " + this.getNombre() + " procesando excusa INVEROSIMIL.");

        this.modoResolucion.ejecutarAccionAdicional(excusa);
        enviarNotificacion(excusa, excusa.getEmpleado().getEmail());
        AdministradorProntuarios.getInstance().guardarProntuario(excusa);
    }

    @Override
    protected String asunto(Excusa excusa) {
        return "Sobre su reciente y creativa excusa";
    }

    @Override
    protected String cuerpo(Excusa excusa) {
        return "Estimado/a " + excusa.getEmpleado().getNombre() + ",\n\nSu excusa ha sido aprobada por su notable creatividad. Buen trabajo.\n\nAtte. El CEO.";
    }

    @Override
    public void actualizar(Prontuario prontuario) {
        System.out.println("CEO " + this.getNombre() + " notificado sobre nuevo prontuario: " +
                prontuario.getExcusa().getClass().getSimpleName());
    }
}