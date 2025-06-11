// ruta: src/test/java/com/excusas/ObserverYSingletonTest.java
package com.excusas;

import com.excusas.empleados.Empleado;
import com.excusas.empleados.encargados.CEO;
import com.excusas.empleados.encargados.modos.ModoNormal;
import com.excusas.excusas.Excusa;
import com.excusas.excusas.MotivoExcusa;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.mail.EmailSender;
import com.excusas.prontuario.AdministradorProntuarios;
import com.excusas.prontuario.Observer;
import com.excusas.prontuario.Prontuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;



@DisplayName("📦 Pruebas de Prontuarios (Observer y Singleton)")
@ExtendWith(MockitoExtension.class)
public class ObserverYSingletonTest {

    @Mock
    private EmailSender emailSender;
    @Mock
    private Observer observerMock;

    private AdministradorProntuarios administrador;
    private Empleado empleado;
    private CEO ceo;

    @BeforeEach
    void setUp() {
        AdministradorProntuarios.reset();
        administrador = AdministradorProntuarios.getInstance();
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
        assertSame(administrador, otraInstancia, "getInstance() debe retornar siempre la misma instancia.");
    }

    @Test
    @DisplayName("📥 Debería crear y guardar un prontuario desde una excusa")
    void deberiaCrearYGuardarProntuarioDesdeExcusa() {

        Excusa excusaMock = mock(Excusa.class);
        when(excusaMock.getEmpleado()).thenReturn(empleado);


        administrador.guardarProntuario(excusaMock);


        assertEquals(1, administrador.getProntuarios().size());
        Prontuario prontuarioGuardado = administrador.getProntuarios().get(0);
        assertSame(empleado, prontuarioGuardado.getEmpleado());
        assertSame(excusaMock, prontuarioGuardado.getExcusa());
    }

    @Test
    @DisplayName("🔔 Debería notificar a los observers al guardar un prontuario desde una excusa")
    void deberiaNotificarObservers() {
        administrador.agregarObserver(observerMock);
        Excusa excusaMock = mock(Excusa.class);
        when(excusaMock.getEmpleado()).thenReturn(empleado);

        administrador.guardarProntuario(excusaMock);

        verify(observerMock).actualizar(any(Prontuario.class));
    }

    @Test
    @DisplayName("🗑️ Debería permitir quitar observers para que no reciban notificaciones")
    void deberiaPermitirQuitarObservers() {
        administrador.agregarObserver(observerMock);
        administrador.quitarObserver(observerMock);
        Excusa excusaMock = mock(Excusa.class);
        when(excusaMock.getEmpleado()).thenReturn(empleado);

        administrador.guardarProntuario(excusaMock);

        verify(observerMock, never()).actualizar(any());
    }


    @Test
    @DisplayName("👑 El CEO debería procesar una excusa inverosímil, crear un prontuario y ser notificado")
    void deberiaProcesarExcusaYNotificarAlMismoCEO() {
        ExcusaInverosimil excusa = new ExcusaInverosimil(empleado, MotivoExcusa.INCREIBLE_INVEROSIMIL);
        CEO ceoSpy = spy(ceo);
        AdministradorProntuarios.getInstance().agregarObserver(ceoSpy);

        ceoSpy.manejarExcusa(excusa);

        assertEquals(1, administrador.getProntuarios().size());
        verify(ceoSpy).actualizar(any(Prontuario.class));
    }

    @Test
    @DisplayName("🗣️ El CEO debería notificar a otros observers (otros CEOs) al crear un prontuario")
    void deberiaNotificarAOtrosCEOs() {
        administrador.agregarObserver(observerMock);
        ExcusaInverosimil excusa = new ExcusaInverosimil(empleado, MotivoExcusa.INCREIBLE_INVEROSIMIL);

        ceo.manejarExcusa(excusa);

        verify(observerMock).actualizar(any(Prontuario.class));
    }
}