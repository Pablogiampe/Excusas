package com.excusas.empleados;

import com.excusas.empleados.encargados.LineaEncargados;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;
import com.excusas.excusas.complejas.ExcusaCompleja;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.excusas.moderadas.ExcusaPerdidaSuministro;
import com.excusas.excusas.moderadas.ExcusaCuidadoFamiliar;
import com.excusas.excusas.triviales.ExcusaTrivial;

public class Empleado implements IEmpleado {
    private final String nombre;
    private final String email;
    private final int legajo;

    public Empleado(String nombre, String email, int legajo) {
        this.nombre = nombre;
        this.email = email;
        this.legajo = legajo;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public int getLegajo() {
        return legajo;
    }

    @Override
    public Excusa generarYEnviarExcusa(MotivoExcusa motivo, LineaEncargados linea) {
        Excusa excusa = crearExcusaPorMotivo(motivo);

        System.out.println("--- NUEVA EXCUSA GENERADA ---");
        System.out.println("Empleado: " + this.nombre + " ha generado una excusa de tipo: " + excusa.getClass().getSimpleName());

        linea.manejarExcusa(excusa);

        return excusa;
    }

    private Excusa crearExcusaPorMotivo(MotivoExcusa motivo) {
        switch (motivo) {
            case QUEDARSE_DORMIDO:
            case PERDI_TRANSPORTE:
                return new ExcusaTrivial(this, motivo);

            case PERDIDA_SUMINISTRO:
                return new ExcusaPerdidaSuministro(this);

            case CUIDADO_FAMILIAR:
                return new ExcusaCuidadoFamiliar(this);

            case IRRELEVANTE:
                return new ExcusaCompleja(this, motivo);

            case INCREIBLE_INVEROSIMIL:
                return new ExcusaInverosimil(this, motivo);

            default:
                throw new IllegalArgumentException("Motivo de excusa no soportado: " + motivo);
        }
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", legajo=" + legajo +
                '}';
    }
}
