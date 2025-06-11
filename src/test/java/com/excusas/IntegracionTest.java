package com.excusas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.LineaEncargados;
import com.excusas.excusas.MotivoExcusa;
import com.excusas.mail.EmailSender;
import com.excusas.prontuario.AdministradorProntuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("⚙️ Pruebas de Integración del Flujo Completo")
@ExtendWith(MockitoExtension.class)
public class IntegracionTest {
    @Mock
    private EmailSender emailSender;

    private LineaEncargados linea;
    private Empleado empleadoTrivial, empleadoModerado, empleadoInverosimil;

    @BeforeEach
    void setUp() {
        AdministradorProntuarios.reset();

        // CAMBIO: Se instancia la línea de encargados pasándole el mock de EmailSender
        linea = new LineaEncargados(emailSender);

        // Ya no necesitamos crear los encargados aquí, porque se crean dentro de LineaEncargados
        empleadoTrivial = new Empleado("Juan Pérez", "juan@empresa.com", 2001);
        empleadoModerado = new Empleado("Ana Torres", "ana.torres@empresa.com", 2002);
        empleadoInverosimil = new Empleado("Sofia Ruiz", "sofia@empresa.com", 2004);
    }

    @Test
    @DisplayName("➡️ Flujo Trivial: Recepcionista debería manejar una excusa TRIVIAL")
    void deberiaProcesarExcusaTrivialCorrectamente() {
        empleadoTrivial.generarYEnviarExcusa(MotivoExcusa.QUEDARSE_DORMIDO, linea);

        verify(emailSender).enviarEmail(
                eq(empleadoTrivial.getEmail()),
                eq("ana@excusas.com"), // Email de la Recepcionista
                eq("Notificación de excusa aceptada"),
                anyString()
        );
        verify(emailSender, times(1)).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("➡️ Flujo Moderado: Supervisor debería manejar una excusa MODERADA")
    void deberiaProcesarExcusaModeradaCorrectamente() {
        empleadoModerado.generarYEnviarExcusa(MotivoExcusa.PERDIDA_SUMINISTRO, linea);

        verify(emailSender).enviarEmail(
                eq("EDESUR@mailfake.com.ar"),
                eq("carlos@excusas.com"), // Email del Supervisor
                eq("Consulta sobre corte de suministro"),
                anyString()
        );
        verify(emailSender).enviarEmail(
                eq(empleadoModerado.getEmail()),
                eq("carlos@excusas.com"), // Email del Supervisor
                eq("Excusa moderada en revisión"),
                anyString()
        );
        verify(emailSender, times(2)).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("➡️ Flujo Inverosímil: CEO debería manejar la excusa y crear un prontuario")
    void deberiaProcesarExcusaInverosimilCorrectamente() {
        empleadoInverosimil.generarYEnviarExcusa(MotivoExcusa.INCREIBLE_INVEROSIMIL, linea);

        verify(emailSender).enviarEmail(
                eq(empleadoInverosimil.getEmail()),
                eq("roberto@excusas.com"), // Email del CEO
                eq("Sobre su reciente y creativa excusa"),
                anyString()
        );
        assertEquals(1, AdministradorProntuarios.getInstance().getProntuarios().size());
        assertEquals(empleadoInverosimil, AdministradorProntuarios.getInstance().getProntuarios().get(0).getEmpleado());
    }
}