package com.excusas.empleados.encargados;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.modos.IModo;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.complejas.ExcusaCompleja;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.excusas.moderadas.ExcusaModerada;
import com.excusas.excusas.moderadas.ExcusaPerdidaSuministro;
import com.excusas.excusas.moderadas.ExcusaCuidadoFamiliar;
import com.excusas.excusas.triviales.ExcusaTrivial;
import com.excusas.mail.EmailSender;

public abstract class EncargadoBase extends Empleado implements ManejadorExcusas {
    protected ManejadorExcusas siguiente;
    protected IModo modo;
    protected EmailSender emailSender;

    public EncargadoBase(String nombre, String email, int legajo,
                         IModo modo, EmailSender emailSender) {
        super(nombre, email, legajo);
        this.modo = modo;
        this.emailSender = emailSender;
    }

    public void setSiguiente(ManejadorExcusas siguiente) {
        this.siguiente = siguiente;
    }
    
    public ManejadorExcusas getSiguiente() {
        return siguiente;
    }

    @Override
    public final void manejarExcusa(Excusa excusa) {
        modo.manejar(this, excusa);
    }

    public final void procesar(Excusa excusa) {
        boolean procesada = excusa.serManejadaPor(this);
        
        if (!procesada) {
            siguiente.manejarExcusa(excusa);
        }
    }

    @Override
    public boolean manejar(ExcusaTrivial excusa) {
        return false;
    }

    @Override
    public boolean manejar(ExcusaModerada excusa) {
        return false;
    }

    @Override
    public boolean manejar(ExcusaPerdidaSuministro excusa) {
        return false;
    }

    @Override
    public boolean manejar(ExcusaCuidadoFamiliar excusa) {
        return false;
    }

    @Override
    public boolean manejar(ExcusaCompleja excusa) {
        return false;
    }

    @Override
    public boolean manejar(ExcusaInverosimil excusa) {
        return false;
    }

    protected void enviarNotificacion(Excusa excusa, String destinatario) {
        emailSender.enviarEmail(
                destinatario,
                this.getEmail(),
                this.asunto(excusa),
                this.cuerpo(excusa)
        );
    }

    public EmailSender getEmailSender() {
        return emailSender;
    }

    protected abstract String asunto(Excusa excusa);
    protected abstract String cuerpo(Excusa excusa);
}
