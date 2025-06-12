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
    private Empleado empleadoTrivial, empleadoModerado, empleadoCuidadoFamiliar, empleadoInverosimil;

    @BeforeEach
    void setUp() {
        AdministradorProntuarios.reset();

        linea = new LineaEncargados(emailSender);

        empleadoTrivial = new Empleado("Juan Pérez", "juan@empresa.com", 2001);
        empleadoModerado = new Empleado("Ana Torres", "ana.torres@empresa.com", 2002);
        empleadoCuidadoFamiliar = new Empleado("Carlos Ruiz", "carlos.ruiz@empresa.com", 2003);
        empleadoInverosimil = new Empleado("Sofia Ruiz", "sofia@empresa.com", 2004);
    }

    @Test
    @DisplayName("➡️ Flujo Trivial: Recepcionista debería manejar una excusa TRIVIAL")
    void deberiaProcesarExcusaTrivialCorrectamente() {
        empleadoTrivial.generarYEnviarExcusa(MotivoExcusa.QUEDARSE_DORMIDO, linea);

        verify(emailSender).enviarEmail(
                eq(empleadoTrivial.getEmail()),
                eq("ana@excusas.com"),
                eq("Notificación de excusa aceptada"),
                anyString()
        );
        verify(emailSender, times(1)).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("➡️ Flujo Moderado - Pérdida Suministro: Supervisor debería consultar a EDESUR")
    void deberiaProcesarExcusaPerdidaSuministroCorrectamente() {
        empleadoModerado.generarYEnviarExcusa(MotivoExcusa.PERDIDA_SUMINISTRO, linea);

        // Verifica que se envió email a EDESUR
        verify(emailSender).enviarEmail(
                eq("EDESUR@mailfake.com.ar"),
                eq("carlos@excusas.com"),
                eq("Consulta sobre corte de suministro"),
                anyString()
        );
        
        // Verifica que se notificó al empleado
        verify(emailSender).enviarEmail(
                eq(empleadoModerado.getEmail()),
                eq("carlos@excusas.com"),
                eq("Excusa moderada en revisión"),
                anyString()
        );
        
        verify(emailSender, times(3)).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("➡️ Flujo Moderado - Cuidado Familiar: Supervisor debería consultar a RRHH")
    void deberiaProcesarExcusaCuidadoFamiliarCorrectamente() {
        empleadoCuidadoFamiliar.generarYEnviarExcusa(MotivoExcusa.CUIDADO_FAMILIAR, linea);

        // Verifica que se envió email a RRHH
        verify(emailSender).enviarEmail(
                eq("rrhh@excusas.com"),
                eq("carlos@excusas.com"),
                eq("Verificación de situación familiar"),
                anyString()
        );
        
        // Verifica que se notificó al empleado
        verify(emailSender).enviarEmail(
                eq(empleadoCuidadoFamiliar.getEmail()),
                eq("carlos@excusas.com"),
                eq("Excusa moderada en revisión"),
                anyString()
        );
        
        verify(emailSender, times(3)).enviarEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("➡️ Flujo Inverosímil: CEO debería manejar la excusa y crear un prontuario")
    void deberiaProcesarExcusaInverosimilCorrectamente() {
        empleadoInverosimil.generarYEnviarExcusa(MotivoExcusa.INCREIBLE_INVEROSIMIL, linea);

        verify(emailSender).enviarEmail(
                eq(empleadoInverosimil.getEmail()),
                eq("roberto@excusas.com"),
                eq("Sobre su reciente y creativa excusa"),
                anyString()
        );
        assertEquals(1, AdministradorProntuarios.getInstance().getProntuarios().size());
        assertEquals(empleadoInverosimil, AdministradorProntuarios.getInstance().getProntuarios().get(0).getEmpleado());
    }
}
