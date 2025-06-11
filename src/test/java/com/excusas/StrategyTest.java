package com.excusas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.ManejadorExcusas;
import com.excusas.empleados.encargados.Recepcionista;
import com.excusas.empleados.encargados.modos.ModoNormal;
import com.excusas.empleados.encargados.modos.ModoProductivo;
import com.excusas.empleados.encargados.modos.ModoVago;
import com.excusas.excusas.MotivoExcusa;
import com.excusas.excusas.triviales.ExcusaTrivial;
import com.excusas.mail.EmailSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("♟️ Pruebas de Estrategias de Resolución (Strategy)")
@ExtendWith(MockitoExtension.class)
public class StrategyTest {

    @Mock
    private EmailSender emailSender;

    private Empleado empleado;
    private ExcusaTrivial excusa;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        empleado = new Empleado("Test User", "test@empresa.com", 1001);
        excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);
        System.setOut(new PrintStream(outputStreamCaptor)); // Redirigir System.out
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("😐 Modo Normal: Debería procesar la excusa sin ejecutar acciones extra")
    void modoNormalDeberiaProcesarSinAccionExtra() {
        ModoNormal modo = new ModoNormal();
        Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, modo, emailSender);

        recepcionista.manejarExcusa(excusa);

        verify(emailSender).enviarEmail(
                eq("test@empresa.com"),
                eq("ana@excusas.com"),
                anyString(),
                anyString()
        );
        assertTrue(outputStreamCaptor.toString().trim().contains("Recepcionista Ana procesando excusa TRIVIAL."));
    }

    @Test
    @DisplayName("😴 Modo Vago: Debería procesar la excusa igualmente, sin acciones extra")
    void modoVagoDeberiaProcesarNormalmente() {
        ModoVago modo = new ModoVago();
        Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, modo, emailSender);
        ManejadorExcusas siguienteEncargado = mock(ManejadorExcusas.class);
        recepcionista.setSiguiente(siguienteEncargado);

        recepcionista.manejarExcusa(excusa);

        verify(emailSender).enviarEmail(
                eq("test@empresa.com"),
                eq("ana@excusas.com"),
                anyString(),
                anyString()
        );

        verify(siguienteEncargado, never()).manejarExcusa(any());
    }

    @Test
    @DisplayName("🚀 Modo Productivo: Debería procesar y loggear la acción adicional")
    void modoProductivoDeberiaLoggearAccion() {
        ModoProductivo modo = new ModoProductivo();
        Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, modo, emailSender);

        recepcionista.manejarExcusa(excusa);

        verify(emailSender).enviarEmail(
                eq("test@empresa.com"),
                eq("ana@excusas.com"),
                anyString(),
                anyString()
        );

        String consoleOutput = outputStreamCaptor.toString().trim();
        assertTrue(consoleOutput.contains("LOG [Modo Productivo]: Se está procesando una excusa para el empleado: Test User"));
    }
}