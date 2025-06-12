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
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("😐 Modo Normal: Debería procesar la excusa normalmente")
    void modoNormalDeberiaProcesarNormalmente() {
        ModoNormal modo = new ModoNormal();
        Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, modo, emailSender);

        recepcionista.manejarExcusa(excusa);

        // Verifica que se procesó la excusa
        verify(emailSender).enviarEmail(
                eq("test@empresa.com"),
                eq("ana@excusas.com"),
                anyString(),
                anyString()
        );
        
        // Verifica que NO se envió email al CTO
        verify(emailSender, never()).enviarEmail(
                eq("cto@excusas.com"),
                anyString(),
                anyString(),
                anyString()
        );
        
        assertTrue(outputStreamCaptor.toString().trim().contains("Recepcionista Ana procesando excusa TRIVIAL."));
    }

    @Test
    @DisplayName("😴 Modo Vago: Debería pasar la excusa al siguiente sin procesarla")
    void modoVagoDeberiaPasarSinProcesar() {
        ModoVago modo = new ModoVago();
        Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, modo, emailSender);
        ManejadorExcusas siguienteEncargado = mock(ManejadorExcusas.class);
        recepcionista.setSiguiente(siguienteEncargado);

        recepcionista.manejarExcusa(excusa);

        // Verifica que pasó al siguiente
        verify(siguienteEncargado).manejarExcusa(excusa);
        
        // Verifica que NO procesó la excusa (no envió email al empleado)
        verify(emailSender, never()).enviarEmail(
                eq("test@empresa.com"),
                anyString(),
                anyString(),
                anyString()
        );
        
        assertTrue(outputStreamCaptor.toString().trim().contains("LOG [Modo Vago]: El encargado Ana está en modo vago"));
    }

    @Test
    @DisplayName("🚀 Modo Productivo: Debería procesar y enviar email adicional al CTO")
    void modoProductivoDeberiaEnviarEmailAdicional() {
        ModoProductivo modo = new ModoProductivo();
        Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, modo, emailSender);

        recepcionista.manejarExcusa(excusa);

        // Verifica que se procesó la excusa normalmente
        verify(emailSender).enviarEmail(
                eq("test@empresa.com"),
                eq("ana@excusas.com"),
                anyString(),
                anyString()
        );

        // Verifica que se envió el email adicional al CTO
        verify(emailSender).enviarEmail(
                eq("cto@excusas.com"),
                eq("ana@excusas.com"),
                eq("Notificación de excusa procesada"),
                contains("Test User")
        );

        // Verifica que se imprimió el log
        String consoleOutput = outputStreamCaptor.toString().trim();
        assertTrue(consoleOutput.contains("LOG [Modo Productivo]: Se está procesando una excusa para el empleado: Test User"));
    }
}
