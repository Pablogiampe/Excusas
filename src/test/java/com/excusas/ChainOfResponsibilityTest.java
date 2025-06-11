// ruta: src/test/java/com/excusas/ChainOfResponsibilityTest.java
package com.excusas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.*;
import com.excusas.empleados.encargados.modos.ModoNormal;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;
import com.excusas.excusas.moderadas.ExcusaModerada;
import com.excusas.excusas.triviales.ExcusaTrivial;
import com.excusas.mail.EmailSender;
import com.excusas.excepciones.ErrorConfiguracion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("🔗 Pruebas de Cadena de Responsabilidad (Chain of Responsibility)")
@ExtendWith(MockitoExtension.class)
public class ChainOfResponsibilityTest {

    @Mock
    private EmailSender emailSender;

    private LineaEncargados linea;
    private Recepcionista recepcionista;
    private SupervisorArea supervisor;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        linea = new LineaEncargados();
        empleado = new Empleado("Juan Pérez", "juan@empresa.com", 2001);

        // Creamos instancias reales de los encargados
        recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, new ModoNormal(), emailSender);
        supervisor = new SupervisorArea("Carlos", "carlos@excusas.com", 1002, new ModoNormal(), emailSender);
    }

    @Test
    @DisplayName("✅ El primer encargado debería manejar la excusa si es responsable")
    void deberiaManejarExcusaElPrimerEncargado() {
        linea.crearCadena(recepcionista, supervisor);
        ExcusaTrivial excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);

        linea.manejarExcusa(excusa);

        verify(emailSender).enviarEmail(
                eq(empleado.getEmail()),
                eq(recepcionista.getEmail()), // Verificamos que el remitente es el recepcionista
                anyString(),
                anyString()
        );
        verify(emailSender, times(1)).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("➡️ Debería pasar la excusa al siguiente si el primero no es responsable")
    void deberiaPasarExcusaAlSiguiente() {
        linea.crearCadena(recepcionista, supervisor);
        ExcusaModerada excusa = new ExcusaModerada(empleado, MotivoExcusa.CUIDADO_FAMILIAR);

        linea.manejarExcusa(excusa);

        verify(emailSender).enviarEmail(
                eq(empleado.getEmail()),
                eq(supervisor.getEmail()), // El remitente debe ser el supervisor.
                anyString(),
                anyString()
        );
        verify(emailSender, times(1)).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("❌ Debería lanzar ErrorConfiguracion si la cadena no fue inicializada")
    void deberiaLanzarErrorSiLaCadenaNoEstaInicializada() {
        Excusa excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);

        ErrorConfiguracion exception = assertThrows(
                ErrorConfiguracion.class,
                () -> linea.manejarExcusa(excusa) // Intentamos usar la cadena sin crearla.
        );
        assertEquals("La cadena de encargados no ha sido inicializada", exception.getMessage());
    }

    @Test
    @DisplayName("❌ Debería lanzar ErrorConfiguracion si la cadena se intenta crear vacía")
    void deberiaLanzarExcepcionConCadenaVacia() {
        assertThrows(ErrorConfiguracion.class, () -> linea.crearCadena());
    }
}