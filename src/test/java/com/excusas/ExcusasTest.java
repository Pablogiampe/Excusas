package com.excusas;

import com.excusas.empleados.Empleado;
import com.excusas.excusas.MotivoExcusa;
import com.excusas.excusas.complejas.ExcusaCompleja;
import com.excusas.excusas.inverosimiles.ExcusaInverosimil;
import com.excusas.excusas.moderadas.ExcusaModerada;
import com.excusas.excusas.triviales.ExcusaTrivial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("📄 Pruebas de Creación de Excusas")
public class ExcusasTest {
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = new Empleado("Test User", "test@empresa.com", 3001);
    }

    @Test
    @DisplayName("✅ Debería crear una Excusa Trivial con un motivo válido")
    void deberiaCrearExcusaTrivialConMotivoValido() {
        ExcusaTrivial excusa = new ExcusaTrivial(empleado, MotivoExcusa.QUEDARSE_DORMIDO);
        // CAMBIO: En lugar de getTipo(), verificamos que la clase sea la correcta
        assertTrue(excusa instanceof ExcusaTrivial);
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
        // CAMBIO: Verificamos la instancia
        assertTrue(excusa instanceof ExcusaModerada);
        assertEquals(MotivoExcusa.PERDIDA_SUMINISTRO, excusa.getMotivo());
    }

    @Test
    @DisplayName("✅ Debería crear una Excusa Compleja con un motivo válido")
    void deberiaCrearExcusaComplejaConMotivoValido() {
        ExcusaCompleja excusa = new ExcusaCompleja(empleado, MotivoExcusa.IRRELEVANTE);
        // CAMBIO: Verificamos la instancia
        assertTrue(excusa instanceof ExcusaCompleja);
        assertEquals(MotivoExcusa.IRRELEVANTE, excusa.getMotivo());
    }

    @Test
    @DisplayName("✅ Debería crear una Excusa Inverosímil con un motivo válido")
    void deberiaCrearExcusaInverosimilConMotivoValido() {
        ExcusaInverosimil excusa = new ExcusaInverosimil(empleado, MotivoExcusa.INCREIBLE_INVEROSIMIL);
        // CAMBIO: Verificamos la instancia
        assertTrue(excusa instanceof ExcusaInverosimil);
        assertEquals(MotivoExcusa.INCREIBLE_INVEROSIMIL, excusa.getMotivo());
    }
}