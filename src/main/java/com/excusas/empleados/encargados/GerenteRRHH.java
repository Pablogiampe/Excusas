package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.estrategias.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;

public class GerenteRRHH extends EncargadoBase {

    public GerenteRRHH(String nombre, String email, int legajo, 
                      ModoResolucion modoResolucion, EmailSender emailSender) {
        super(nombre, email, legajo, modoResolucion, emailSender);
    }

    @Override
    protected boolean puedeManear(Excusa excusa) {
        return "COMPLEJA".equals(excusa.getTipo());
    }

    @Override
    protected void procesarExcusa(Excusa excusa) {

        System.out.println("Excusa compleja procesada por Gerente de RRHH para: " + 
                          excusa.getEmpleado().getNombre());
    }
}
