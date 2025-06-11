package com.excusas.empleados.encargados;

import com.excusas.empleados.encargados.modos.ModoNormal;
import com.excusas.empleados.encargados.modos.ModoProductivo;
import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;

public class LineaEncargados {

    private final ManejadorExcusas primerEncargado;

    /**
     * El constructor ahora acepta las dependencias (como EmailSender)
     * para que podamos usar un "mock" en las pruebas.
     */
    public LineaEncargados(EmailSender emailSender) {
        // 1. Se crean las instancias de cada encargado, usando el EmailSender que nos pasaron
        Recepcionista recepcionista = new Recepcionista("Ana García", "ana@excusas.com", 1001, new ModoNormal(), emailSender);
        SupervisorArea supervisor = new SupervisorArea("Carlos López", "carlos@excusas.com", 1002, new ModoProductivo(), emailSender);
        GerenteRRHH gerente = new GerenteRRHH("María Rodríguez", "maria@excusas.com", 1003, new ModoNormal(), emailSender);
        CEO ceo = new CEO("Roberto Silva", "roberto@excusas.com", 1004, new ModoNormal(), emailSender);
        Rechazador rechazador = new Rechazador();

        // 2. Se enlaza la cadena manualmente
        recepcionista.setSiguiente(supervisor);
        supervisor.setSiguiente(gerente);
        gerente.setSiguiente(ceo);
        ceo.setSiguiente(rechazador);

        // 3. Se define cuál es el primer eslabón de la cadena
        this.primerEncargado = recepcionista;
    }

    public void manejarExcusa(Excusa excusa) {
        primerEncargado.manejarExcusa(excusa);
    }
}