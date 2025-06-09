package com.excusas.empleados.encargados;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.estrategias.ModoResolucion;
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
        if (modoResolucion.debeEvaluar(excusa)) {
            modoResolucion.ejecutarAccionAdicional(excusa, emailSender);
            
            if (puedeManear(excusa)) {
                procesarExcusa(excusa);
                return;
            }
        }
        
        if (siguiente != null) {
            siguiente.manejarExcusa(excusa);
        }
    }

    protected abstract boolean puedeManear(Excusa excusa);
    protected abstract void procesarExcusa(Excusa excusa);
}
