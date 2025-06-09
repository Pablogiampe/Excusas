package com.excusas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.*;
import com.excusas.empleados.encargados.estrategias.ModoNormal;
import com.excusas.empleados.encargados.estrategias.ModoProductivo;
import com.excusas.empleados.encargados.estrategias.ModoVago;
import com.excusas.excusas.*;
import com.excusas.excusas.complejas.ExcusaCompleja;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.excusas.moderadas.ExcusaModerada;
import com.excusas.excusas.triviales.ExcusaTrivial;
import com.excusas.mail.EmailSender;
import com.excusas.mail.EmailSenderImpl;
import com.excusas.prontuario.AdministradorProntuarios;
import com.excusas.prontuario.Observer;
import com.excusas.prontuario.Prontuario;
import com.excusas.excepciones.ErrorConfiguracion;
import com.excusas.excepciones.ErrorProcesamiento;
import com.excusas.sistema.SistemaExcusas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("▶️ Pruebas del Sistema de Gestión de Excusas")
@ExtendWith(MockitoExtension.class)
public class SistemaExcusasTest {

    @Mock
    private EmailSender emailSender;


    @Nested
    @DisplayName("📄 Pruebas de Creación de Excusas")
    class ExcusasTest {
        private Empleado empleado;

        @BeforeEach
        void setUp() {
            empleado = new Empleado("Test User", "test@empresa.com", 3001);
        }

        @Test
        @DisplayName("✅ Debería crear una Excusa Trivial con un motivo válido")
        void deberiaCrearExcusaTrivialConMotivoValido() {
            ExcusaTrivial excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);
            assertEquals("TRIVIAL", excusa.getTipo());
            assertEquals(MotivoExcusa.QUEDARSE_DORMIDO, excusa.getMotivo());
            assertEquals(empleado, excusa.getEmpleado());
        }

        @Test
        @DisplayName("❌ Debería lanzar excepción al crear Excusa Trivial con motivo inválido")
        void deberiaLanzarExcepcionConMotivoInvalidoParaTrivial() {
            assertThrows(IllegalArgumentException.class, () -> new ExcusaTrivial(empleado, MotivoExcusa.IRRELEVANTE));
        }

        @Test
        @DisplayName("✅ Debería crear una Excusa Moderada con un motivo válido")
        void deberiaCrearExcusaModeradaConMotivoValido() {
            ExcusaModerada excusa = new ExcusaModerada(empleado, MotivoExcusa.PERDIDA_SUMINISTRO);
            assertEquals("MODERADA", excusa.getTipo());
            assertEquals(MotivoExcusa.PERDIDA_SUMINISTRO, excusa.getMotivo());
        }

        @Test
        @DisplayName("✅ Debería crear una Excusa Compleja con un motivo válido")
        void deberiaCrearExcusaComplejaConMotivoValido() {
            ExcusaCompleja excusa = new ExcusaCompleja(empleado, MotivoExcusa.IRRELEVANTE);
            assertEquals("COMPLEJA", excusa.getTipo());
            assertEquals(MotivoExcusa.IRRELEVANTE, excusa.getMotivo());
        }

        @Test
        @DisplayName("✅ Debería crear una Excusa Inverosímil con un motivo válido")
        void deberiaCrearExcusaInverosimilConMotivoValido() {
            ExcusaInverosimil excusa = new ExcusaInverosimil(empleado, MotivoExcusa.INCREIBLE_INVEROSIMIL);
            assertEquals("INVEROSIMIL", excusa.getTipo());
            assertEquals(MotivoExcusa.INCREIBLE_INVEROSIMIL, excusa.getMotivo());
        }
    }

    // --- CHAIN OF RESPONSABILITY ---
    @Nested
    @DisplayName("🔗 Pruebas de Cadena de Responsabilidad (Chain of Responsibility)")
    class ChainOfResponsibilityTest {
        private Recepcionista recepcionista;
        private ManejadorExcusas siguienteEncargado;

        @BeforeEach
        void setUp() {
            siguienteEncargado = mock(ManejadorExcusas.class);
            recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, new ModoNormal(), emailSender);
            recepcionista.setSiguiente(siguienteEncargado);
        }

        @Test
        @DisplayName("✅ Debería procesar la excusa que puede manejar y no pasarla al siguiente")
        void deberiaProcesarExcusaQueManeja() {
            Empleado empleado = new Empleado("Juan", "juan@empresa.com", 2001);
            ExcusaTrivial excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);

            recepcionista.manejarExcusa(excusa);

            verify(emailSender).enviarEmail("juan@empresa.com", "ana@excusas.com", "motivo demora", "la licencia fue aceptada");
            verify(siguienteEncargado, never()).manejarExcusa(any());
        }

        @Test
        @DisplayName("➡️ Debería pasar la excusa al siguiente encargado si no puede manejarla")
        void deberiaPasarExcusaQueNoManeja() {
            Excusa excusaNoTrivial = mock(Excusa.class);
            when(excusaNoTrivial.getTipo()).thenReturn("MODERADA");

            recepcionista.manejarExcusa(excusaNoTrivial);

            verify(siguienteEncargado).manejarExcusa(excusaNoTrivial);
            verifyNoInteractions(emailSender);
        }

        @Test
        @DisplayName("👍 Debería crear una cadena de encargados correctamente")
        void deberiaCrearCadenaCorrectamente() {
            LineaEncargados linea = new LineaEncargados();
            Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, new ModoNormal(), emailSender);
            SupervisorArea supervisor = new SupervisorArea("Carlos", "carlos@excusas.com", 1002, new ModoNormal(), emailSender);

            linea.crearCadena(recepcionista, supervisor);

            assertNotNull(linea.getPrimerEncargado());
            assertEquals(recepcionista, linea.getPrimerEncargado());
        }

        @Test
        @DisplayName("❌ Debería lanzar ErrorConfiguracion si la cadena se intenta crear vacía")
        void deberiaLanzarExcepcionConCadenaVacia() {
            LineaEncargados linea = new LineaEncargados();
            // CORRECCIÓN: Se esperaba ErrorConfiguracion, no IllegalArgumentException.
            assertThrows(ErrorConfiguracion.class, linea::crearCadena);
        }
    }

    // --- STRATEGY ---
    @Nested
    @DisplayName("♟️ Pruebas de Estrategias de Resolución (Strategy)")
    class StrategyTest {
        private Empleado empleado;
        private ExcusaTrivial excusa;

        @BeforeEach
        void setUp() {
            empleado = new Empleado("Test User", "test@empresa.com", 1001);
            excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);
        }

        @Test
        @DisplayName("😐 Modo Normal: Debería evaluar y procesar sin acciones extra")
        void modoNormalDeberiaEvaluarYNoHacerAccionAdicional() {
            ModoNormal modo = new ModoNormal();
            Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, modo, emailSender);

            recepcionista.manejarExcusa(excusa);

            verify(emailSender).enviarEmail("test@empresa.com", "ana@excusas.com", "motivo demora", "la licencia fue aceptada");
            verify(emailSender, never()).enviarEmail(eq("cto@excusas.com"), anyString(), anyString(), anyString());
        }

        @Test
        @DisplayName("😴 Modo Vago: No debería evaluar y pasar la excusa al siguiente")
        void modoVagoNoDeberiaEvaluar() {
            ModoVago modo = new ModoVago();
            Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, modo, emailSender);
            var siguienteEncargado = mock(ManejadorExcusas.class);
            recepcionista.setSiguiente(siguienteEncargado);

            recepcionista.manejarExcusa(excusa);

            verify(siguienteEncargado).manejarExcusa(excusa);
            verifyNoInteractions(emailSender);
        }

        @Test
        @DisplayName("🚀 Modo Productivo: Debería procesar y notificar al CTO")
        void modoProductivoDeberiaNotificarCTO() {
            ModoProductivo modo = new ModoProductivo();
            Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, modo, emailSender);

            recepcionista.manejarExcusa(excusa);

            verify(emailSender).enviarEmail("test@empresa.com", "ana@excusas.com", "motivo demora", "la licencia fue aceptada");
            verify(emailSender).enviarEmail("cto@excusas.com", "sistema@excusas.com", "Notificación de Procesamiento", "Se está procesando una excusa en modo productivo para el empleado: Test User");
        }
    }

    // --- OBSERVER/SINGLETON---
    @Nested
    @DisplayName("📦 Pruebas de Prontuarios (Observer y Singleton)")
    class ObserverYSingletonTest {
        private AdministradorProntuarios administrador;
        private Observer observerMock;
        private Empleado empleado;
        private CEO ceo;

        @BeforeEach
        void setUp() {
            AdministradorProntuarios.reset(); // Limpia el estado del Singleton para cada prueba
            administrador = AdministradorProntuarios.getInstance();
            observerMock = mock(Observer.class);
            empleado = new Empleado("Test User", "test@empresa.com", 3001);
            ceo = new CEO("Roberto Silva", "roberto@excusas.com", 1004, new ModoNormal(), emailSender);
        }

        @AfterEach
        void tearDown() {
            AdministradorProntuarios.reset();
        }

        @Test
        @DisplayName("☝️ Debería devolver siempre la misma instancia (Singleton)")
        void deberiaSerSingleton() {
            AdministradorProntuarios otraInstancia = AdministradorProntuarios.getInstance();
            assertSame(administrador, otraInstancia);
        }

        @Test
        @DisplayName("📥 Debería guardar un prontuario correctamente")
        void deberiaGuardarProntuario() {
            Prontuario prontuario = new Prontuario(empleado, mock(Excusa.class), empleado.getLegajo());
            administrador.guardarProntuario(prontuario);
            assertEquals(1, administrador.getProntuarios().size());
            assertSame(prontuario, administrador.getProntuarios().get(0));
        }

        @Test
        @DisplayName("🔔 Debería notificar a los observers al guardar un prontuario")
        void deberiaNotificarObservers() {
            administrador.agregarObserver(observerMock);
            Prontuario prontuario = new Prontuario(empleado, mock(Excusa.class), empleado.getLegajo());
            administrador.guardarProntuario(prontuario);
            verify(observerMock).notificar(prontuario);
        }

        @Test
        @DisplayName("🗑️ Debería permitir quitar observers para que no reciban notificaciones")
        void deberiaPermitirQuitarObservers() {
            administrador.agregarObserver(observerMock);
            administrador.quitarObserver(observerMock);
            Prontuario prontuario = new Prontuario(empleado, mock(Excusa.class), empleado.getLegajo());
            administrador.guardarProntuario(prontuario);
            verify(observerMock, never()).notificar(any());
        }

        @Test
        @DisplayName("👑 El CEO debería procesar una excusa inverosímil y crear un prontuario")
        void deberiaProcesarExcusaInverosimil() {
            ExcusaInverosimil excusa = new ExcusaInverosimil(empleado, MotivoExcusa.INCREIBLE_INVEROSIMIL);

            ceo.manejarExcusa(excusa);

            verify(emailSender).enviarEmail("test@empresa.com", "roberto@excusas.com", "Excusa Aprobada", "Aprobado por creatividad");
            assertEquals(1, administrador.getProntuarios().size());
            assertEquals(empleado, administrador.getProntuarios().get(0).getEmpleado());
        }

        @Test
        @DisplayName("🗣️ El CEO debería notificar a otros observers (otros CEOs) al crear un prontuario")
        void deberiaNotificarAOtrosCEOs() {
            Observer otroCEO = mock(Observer.class);
            administrador.agregarObserver(otroCEO);
            ExcusaInverosimil excusa = new ExcusaInverosimil(empleado, MotivoExcusa.INCREIBLE_INVEROSIMIL);

            ceo.manejarExcusa(excusa);

            verify(otroCEO).notificar(any(Prontuario.class));
        }
    }


    @Nested
    @DisplayName("⚙️ Pruebas de Integración del Flujo Completo")
    class IntegracionTest {
        private LineaEncargados linea;
        private Empleado empleadoTrivial, empleadoModerado, empleadoComplejo, empleadoInverosimil;

        @BeforeEach
        void setUp() {
            AdministradorProntuarios.reset();

            Recepcionista recepcionista = new Recepcionista("Ana García", "ana@excusas.com", 1001, new ModoNormal(), emailSender);
            SupervisorArea supervisor = new SupervisorArea("Carlos López", "carlos@excusas.com", 1002, new ModoProductivo(), emailSender);
            GerenteRRHH gerente = new GerenteRRHH("María Rodríguez", "maria@excusas.com", 1003, new ModoNormal(), emailSender);
            CEO ceo = new CEO("Roberto Silva", "roberto@excusas.com", 1004, new ModoNormal(), emailSender);

            linea = new LineaEncargados();
            linea.crearCadena(recepcionista, supervisor, gerente, ceo);

            empleadoTrivial = new Empleado("Juan Pérez", "juan@empresa.com", 2001);
            empleadoModerado = new Empleado("Ana Torres", "ana.torres@empresa.com", 2002);
            empleadoComplejo = new Empleado("Luis Martín", "luis@empresa.com", 2005);
            empleadoInverosimil = new Empleado("Sofia Ruiz", "sofia@empresa.com", 2004);
        }

        @Test
        @DisplayName("➡️ Recepcionista debería manejar una excusa TRIVIAL")
        void deberiaProcesarExcusaTrivialConRecepcionista() {
            ExcusaTrivial excusa = new ExcusaTrivial(empleadoTrivial, MotivoExcusa.QUEDARSE_DORMIDO);
            linea.getPrimerEncargado().manejarExcusa(excusa);
            verify(emailSender).enviarEmail("juan@empresa.com", "ana@excusas.com", "motivo demora", "la licencia fue aceptada");
            verify(emailSender, never()).enviarEmail(eq("EDESUR@mailfake.com.ar"), anyString(), anyString(), anyString());
        }

        @Test
        @DisplayName("➡️ Supervisor debería manejar una excusa MODERADA")
        void deberiaProcesarExcusaModeradaConSupervisor() {
            ExcusaModerada excusa = new ExcusaModerada(empleadoModerado, MotivoExcusa.PERDIDA_SUMINISTRO);
            linea.getPrimerEncargado().manejarExcusa(excusa);
            verify(emailSender).enviarEmail("cto@excusas.com", "sistema@excusas.com", "Notificación de Procesamiento", "Se está procesando una excusa en modo productivo para el empleado: Ana Torres");
            verify(emailSender).enviarEmail("EDESUR@mailfake.com.ar", "carlos@excusas.com", "Consulta sobre corte de suministro", "Consulta si hubo corte de suministro en la zona del empleado Ana Torres");
        }

        @Test
        @DisplayName("➡️ Gerente de RRHH debería manejar una excusa COMPLEJA")
        void deberiaProcesarExcusaComplejaConGerente() {
            ExcusaCompleja excusa = new ExcusaCompleja(empleadoComplejo, MotivoExcusa.IRRELEVANTE);
            linea.getPrimerEncargado().manejarExcusa(excusa);
            verify(emailSender).enviarEmail("cto@excusas.com", "sistema@excusas.com", "Notificación de Procesamiento", "Se está procesando una excusa en modo productivo para el empleado: Luis Martín");
            verify(emailSender, never()).enviarEmail(eq(empleadoComplejo.getEmail()), anyString(), anyString(), anyString());
        }

        @Test
        @DisplayName("➡️ CEO debería manejar una excusa INVEROSIMIL")
        void deberiaProcesarExcusaInverosimilConCEO() {
            ExcusaInverosimil excusa = new ExcusaInverosimil(empleadoInverosimil, MotivoExcusa.INCREIBLE_INVEROSIMIL);
            linea.getPrimerEncargado().manejarExcusa(excusa);
            verify(emailSender).enviarEmail("cto@excusas.com", "sistema@excusas.com", "Notificación de Procesamiento", "Se está procesando una excusa en modo productivo para el empleado: Sofia Ruiz");
            verify(emailSender).enviarEmail("sofia@empresa.com", "roberto@excusas.com", "Excusa Aprobada", "Aprobado por creatividad");
            assertEquals(1, AdministradorProntuarios.getInstance().getProntuarios().size());
        }

        @Test
        @DisplayName("🛑 El Rechazador debería manejar una excusa no reconocida por nadie")
        void deberiaRechazarExcusaNoManejable() {

            Excusa excusaInvalida = mock(Excusa.class);
            when(excusaInvalida.getTipo()).thenReturn("TIPO_INEXISTENTE");
            when(excusaInvalida.getEmpleado()).thenReturn(new Empleado("Test", "test@test.com", 1));

            linea.getPrimerEncargado().manejarExcusa(excusaInvalida);


            verify(emailSender).enviarEmail(eq("cto@excusas.com"), anyString(), anyString(), anyString());

            verify(emailSender, never()).enviarEmail(eq("test@test.com"), anyString(), anyString(), anyString());
        }
    }


    @Nested
    @DisplayName("💣 Pruebas de Excepciones Personalizadas")
    class ExcepcionesTest {

        @Test
        @DisplayName("❌ Debería lanzar ErrorConfiguracion con cadena de encargados vacía")
        void deberiaLanzarErrorConfiguracionConCadenaVacia() {
            LineaEncargados linea = new LineaEncargados();
            ErrorConfiguracion exception = assertThrows(ErrorConfiguracion.class, linea::crearCadena);
            assertEquals("La cadena de encargados no puede estar vacía", exception.getMessage());
        }

        @Test
        @DisplayName("❌ Debería lanzar ErrorConfiguracion con un encargado nulo en la cadena")
        void deberiaLanzarErrorConfiguracionConEncargadoNulo() {
            LineaEncargados linea = new LineaEncargados();
            Recepcionista recepcionista = new Recepcionista("Ana", "ana@excusas.com", 1001, new ModoNormal(), emailSender);
            ErrorConfiguracion exception = assertThrows(ErrorConfiguracion.class, () -> linea.crearCadena(recepcionista, null));
            assertEquals("El encargado en la posición 1 no puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("❌ Debería lanzar ErrorProcesamiento al guardar un prontuario nulo")
        void deberiaLanzarErrorProcesamientoConProntuarioNulo() {
            AdministradorProntuarios admin = AdministradorProntuarios.getInstance();
            ErrorProcesamiento exception = assertThrows(ErrorProcesamiento.class, () -> admin.guardarProntuario(null));
            assertEquals("No se puede guardar un prontuario nulo", exception.getMessage());
        }

        @Test
        @DisplayName("❌ Debería lanzar ErrorProcesamiento con formato de email inválido")
        void deberiaLanzarErrorProcesamientoConEmailInvalido() {
            EmailSenderImpl realEmailSender = new EmailSenderImpl();
            ErrorProcesamiento exception = assertThrows(ErrorProcesamiento.class, () -> realEmailSender.enviarEmail("email-invalido", "origen@test.com", "Asunto", "Cuerpo"));
            assertTrue(exception.getMessage().contains("El email de destino no tiene un formato válido"));
        }
    }


    @Nested
    @DisplayName("🚀 Pruebas de la clase principal SistemaExcusas")
    class SistemaExcusasIntegracionTest {
        private SistemaExcusas sistema;

        @BeforeEach
        void setUp() {
            AdministradorProntuarios.reset();
            sistema = new SistemaExcusas();
        }

        @Test
        @DisplayName("✅ Debería inicializar el sistema correctamente")
        void deberiaInicializarCorrectamente() {
            sistema.inicializar();
            assertTrue(sistema.estaInicializado());
        }

        @Test
        @DisplayName("❌ Debería lanzar ErrorConfiguracion si se procesa una excusa sin inicializar")
        void deberiaLanzarErrorConfiguracionSinInicializar() {
            Empleado empleado = new Empleado("Test", "test@empresa.com", 1001);
            ExcusaTrivial excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);
            ErrorConfiguracion exception = assertThrows(ErrorConfiguracion.class, () -> sistema.procesarExcusa(excusa));
            assertEquals("El sistema no ha sido inicializado", exception.getMessage());
        }

        @Test
        @DisplayName("❌ Debería lanzar ErrorProcesamiento si la excusa a procesar es nula")
        void deberiaLanzarErrorProcesamientoConExcusaNula() {
            sistema.inicializar();
            ErrorProcesamiento exception = assertThrows(ErrorProcesamiento.class, () -> sistema.procesarExcusa(null));
            assertEquals("No se puede procesar una excusa nula", exception.getMessage());
        }

        @Test
        @DisplayName("👍 Debería procesar una excusa sin lanzar excepciones después de inicializar")
        void deberiaProcesarExcusaCorrectamente() {
            sistema.inicializar();
            Empleado empleado = new Empleado("Juan", "juan@empresa.com", 2001);
            ExcusaTrivial excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);
            assertDoesNotThrow(() -> sistema.procesarExcusa(excusa));
        }
    }
}