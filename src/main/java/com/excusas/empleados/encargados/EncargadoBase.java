package com.excusas.empleados.encargados;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.modos.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;

public abstract class EncargadoBase extends Empleado implements ManejadorExcusas {
    protected ManejadorExcusas siguiente;
    protected ModoResolucion modoResolucion;
    protected EmailSender emailSender;

    public EncargadoBase(String nombre, String email, int legajo,
                         ModoResolucion modoResolucion, EmailSender emailSender) {
        super(nombre, email, legajo);
        this.modoResolucion = modoResolucion;
        this.emailSender = emailSender;
    }

    public void setSiguiente(ManejadorExcusas siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public final void manejarExcusa(Excusa excusa) {
        if (this.esResponsable(excusa)) {
            this.modoResolucion.ejecutarAccionAdicional(excusa);

            this.procesarExcusa(excusa);
        } else if (siguiente != null) {
            siguiente.manejarExcusa(excusa);
        }
    }


    protected abstract boolean esResponsable(Excusa excusa);

    protected abstract void procesarExcusa(Excusa excusa);


    protected void enviarNotificacion(Excusa excusa, String destinatario) {
        emailSender.enviarEmail(
                destinatario,
                this.getEmail(),
                this.asunto(excusa),
                this.cuerpo(excusa)
        );
    }


    protected abstract String asunto(Excusa excusa);

    protected abstract String cuerpo(Excusa excusa);
}