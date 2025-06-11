package com.excusas.empleados.encargados;

import com.excusas.excusas.Excusa;
import com.excusas.excusas.complejas.ExcusaCompleja;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.excusas.moderadas.ExcusaModerada;
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
    public void manejar(ExcusaTrivial excusa) {
        rechazar();
    }

    @Override
    public void manejar(ExcusaModerada excusa) {
        rechazar();
    }

    @Override
    public void manejar(ExcusaCompleja excusa) {
        rechazar();
    }

    @Override
    public void manejar(ExcusaInverosimil excusa) {
        rechazar();
    }
}