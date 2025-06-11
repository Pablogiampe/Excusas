package com.excusas.empleados.encargados;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.modos.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.complejas.ExcusaCompleja;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.excusas.moderadas.ExcusaModerada;
import com.excusas.excusas.triviales.ExcusaTrivial;
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
        excusa.serManejadaPor(this);
    }

    @Override
    public void manejar(ExcusaTrivial excusa) {
        pasarAlSiguiente(excusa); // Por defecto, no la manejo
    }

    @Override
    public void manejar(ExcusaModerada excusa) {
        pasarAlSiguiente(excusa); // Por defecto, no la manejo
    }

    @Override
    public void manejar(ExcusaCompleja excusa) {
        pasarAlSiguiente(excusa); // Por defecto, no la manejo
    }

    @Override
    public void manejar(ExcusaInverosimil excusa) {
        pasarAlSiguiente(excusa); // Por defecto, no la manejo
    }

    private void pasarAlSiguiente(Excusa excusa) {
        if (siguiente != null) {
            siguiente.manejarExcusa(excusa);
        }
    }

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