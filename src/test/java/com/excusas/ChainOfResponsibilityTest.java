package com.excusas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.LineaEncargados;
import com.excusas.excusas.MotivoExcusa;
import com.excusas.excusas.moderadas.ExcusaModerada;
import com.excusas.excusas.triviales.ExcusaTrivial;
import com.excusas.mail.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.excusas.excusas.moderadas.ExcusaCuidadoFamiliar;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("🔗 Pruebas de Cadena de Responsabilidad (Chain of Responsibility)")
@ExtendWith(MockitoExtension.class)
public class ChainOfResponsibilityTest {

    @Mock
    private EmailSender emailSender;

    private LineaEncargados linea;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        // CAMBIO: Se instancia la línea directamente con el mock de EmailSender
        linea = new LineaEncargados(emailSender);
        empleado = new Empleado("Juan Pérez", "juan@empresa.com", 2001);
    }

    @Test
    @DisplayName("✅ El primer encargado debería manejar la excusa si es responsable")
    void deberiaManejarExcusaElPrimerEncargado() {
        // CAMBIO: Ya no se llama a crearCadena()
        ExcusaTrivial excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);
        linea.manejarExcusa(excusa);

        // Verificamos que se envió el email con los datos correctos (hardcodeados en LineaEncargados)
        verify(emailSender).enviarEmail(
                eq(empleado.getEmail()),
                eq("ana@excusas.com"), // Email de la Recepcionista
                anyString(),
                anyString()
        );
        verify(emailSender, times(1)).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("➡️ Debería pasar la excusa al siguiente si el primero no es responsable")
    void deberiaPasarExcusaAlSiguiente() {
        // CAMBIO: Ya no se llama a crearCadena()
        ExcusaCuidadoFamiliar excusa = new ExcusaCuidadoFamiliar(empleado);
        linea.manejarExcusa(excusa);

        // Verificamos que el email lo envía el Supervisor
        verify(emailSender).enviarEmail(
                eq(empleado.getEmail()),
                eq("carlos@excusas.com"), // Email del Supervisor
                anyString(),
                anyString()
        );
        verify(emailSender, times(1)).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }
}
