package com.excusas.sistema;

import com.excusas.empleados.encargados.*;
import com.excusas.empleados.encargados.estrategias.ModoNormal;
import com.excusas.empleados.encargados.estrategias.ModoProductivo;

import com.excusas.excepciones.ErrorConfiguracion;
import com.excusas.excepciones.ErrorProcesamiento;
import com.excusas.excusas.Excusa;
import com.excusas.mail.EmailSender;
import com.excusas.mail.EmailSenderImpl;

public class SistemaExcusas {
    private LineaEncargados lineaEncargados;
    private EmailSender emailSender;
    private boolean inicializado = false;

    public SistemaExcusas() {
        this.emailSender = new EmailSenderImpl();
        this.lineaEncargados = new LineaEncargados();
    }

    public void inicializar() {
        try {

            Recepcionista recepcionista = new Recepcionista(
                "Ana García", "ana@excusas.com", 1001, 
                new ModoNormal(), emailSender
            );
            
            SupervisorArea supervisor = new SupervisorArea(
                "Carlos López", "carlos@excusas.com", 1002, 
                new ModoProductivo(), emailSender
            );
            
            GerenteRRHH gerente = new GerenteRRHH(
                "María Rodríguez", "maria@excusas.com", 1003, 
                new ModoNormal(), emailSender
            );
            
            CEO ceo = new CEO(
                "Roberto Silva", "roberto@excusas.com", 1004, 
                new ModoNormal(), emailSender
            );
            

            lineaEncargados.crearCadena(recepcionista, supervisor, gerente, ceo);
            inicializado = true;
            
        } catch (Exception e) {
            throw new ErrorConfiguracion("Error al inicializar el sistema: " + e.getMessage());
        }
    }

    public void procesarExcusa(Excusa excusa) {
        if (!inicializado) {
            throw new ErrorConfiguracion("El sistema no ha sido inicializado");
        }
        
        if (excusa == null) {
            throw new ErrorProcesamiento("No se puede procesar una excusa nula");
        }
        
        if (excusa.getEmpleado() == null) {
            throw new ErrorProcesamiento("La excusa debe tener un empleado asociado");
        }
        
        try {
            lineaEncargados.getPrimerEncargado().manejarExcusa(excusa);
        } catch (Exception e) {
            throw new ErrorProcesamiento("Error al procesar la excusa: " + e.getMessage(), e);
        }
    }

    public boolean estaInicializado() {
        return inicializado;
    }
}
