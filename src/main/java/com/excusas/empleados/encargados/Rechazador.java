package com.excusas.empleados.encargados;

import com.excusas.excusas.Excusa;
import com.excusas.excusas.complejas.ExcusaCompleja;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.excusas.moderadas.ExcusaModerada;
import com.excusas.excusas.moderadas.ExcusaPerdidaSuministro;
import com.excusas.excusas.moderadas.ExcusaCuidadoFamiliar;
import com.excusas.excusas.triviales.ExcusaTrivial;

public class Rechazador implements ManejadorExcusas {

    private void rechazar() {
        System.out.println("Excusa rechazada: ningún encargado pudo procesarla.");
    }

    @Override
    public void manejarExcusa(Excusa excusa) {
        excusa.serManejadaPor(this);
    }

    @Override
    public boolean manejar(ExcusaTrivial excusa) {
        rechazar();
        return true;
    }

    @Override
    public boolean manejar(ExcusaModerada excusa) {
        rechazar();
        return true;
    }

    @Override
    public boolean manejar(ExcusaPerdidaSuministro excusa) {
        rechazar();
        return true;
    }

    @Override
    public boolean manejar(ExcusaCuidadoFamiliar excusa) {
        rechazar();
        return true;
    }

    @Override
    public boolean manejar(ExcusaCompleja excusa) {
        rechazar();
        return true;
    }

    @Override
    public boolean manejar(ExcusaInverosimil excusa) {
        rechazar();
        return true;
    }
}
