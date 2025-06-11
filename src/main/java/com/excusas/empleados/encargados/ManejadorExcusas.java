package com.excusas.empleados.encargados;

import com.excusas.excusas.Excusa;
import com.excusas.excusas.complejas.ExcusaCompleja;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.excusas.moderadas.ExcusaModerada;
import com.excusas.excusas.triviales.ExcusaTrivial;

public interface ManejadorExcusas {
    void manejarExcusa(Excusa excusa);

    void manejar(ExcusaTrivial excusa);
    void manejar(ExcusaModerada excusa);
    void manejar(ExcusaCompleja excusa);
    void manejar(ExcusaInverosimil excusa);
}