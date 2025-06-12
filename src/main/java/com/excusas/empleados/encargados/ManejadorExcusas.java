package com.excusas.empleados.encargados;

import com.excusas.excusas.Excusa;
import com.excusas.excusas.complejas.ExcusaCompleja;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.excusas.moderadas.ExcusaModerada;
import com.excusas.excusas.moderadas.ExcusaPerdidaSuministro;
import com.excusas.excusas.moderadas.ExcusaCuidadoFamiliar;
import com.excusas.excusas.triviales.ExcusaTrivial;

public interface ManejadorExcusas {
    void manejarExcusa(Excusa excusa);

    // Los métodos manejar ahora retornan boolean para indicar si procesaron la excusa
    boolean manejar(ExcusaTrivial excusa);
    boolean manejar(ExcusaModerada excusa);
    boolean manejar(ExcusaPerdidaSuministro excusa);
    boolean manejar(ExcusaCuidadoFamiliar excusa);
    boolean manejar(ExcusaCompleja excusa);
    boolean manejar(ExcusaInverosimil excusa);
}
