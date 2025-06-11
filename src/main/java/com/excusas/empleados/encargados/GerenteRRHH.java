package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.modos.ModoResolucion;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.complejas.ExcusaCompleja; // Importar la excusa específica
import com.excusas.mail.EmailSender;

public class GerenteRRHH extends EncargadoBase {

    public GerenteRRHH(String nombre, String email, int legajo,
                       ModoResolucion modoResolucion, EmailSender emailSender) {
        super(nombre, email, legajo, modoResolucion, emailSender);
    }

    // Sobrescribimos SOLAMENTE el método para la excusa que nos interesa
    @Override
    public void manejar(ExcusaCompleja excusa) {
        System.out.println("Gerente de RRHH " + this.getNombre() + " procesando excusa COMPLEJA.");

        // Aplicamos la estrategia y notificamos
        this.modoResolucion.ejecutarAccionAdicional(excusa);
        enviarNotificacion(excusa, excusa.getEmpleado().getEmail());
    }

    @Override
    protected String asunto(Excusa excusa) {
        return "Análisis de excusa compleja";
    }

    @Override
    protected String cuerpo(Excusa excusa) {
        return "Estimado/a " + excusa.getEmpleado().getNombre() + ",\n\nSu excusa requiere un análisis detallado por parte de RRHH. Se le notificará la resolución.\n\nSaludos.";
    }
}