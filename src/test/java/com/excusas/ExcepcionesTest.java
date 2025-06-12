package com.excusas;

import com.excusas.excepciones.ErrorProcesamiento;
import com.excusas.mail.EmailSender;

import com.excusas.prontuario.AdministradorProntuarios;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("💣 Pruebas de Excepciones Personalizadas")
@ExtendWith(MockitoExtension.class)
public class ExcepcionesTest {

    @Mock
    private EmailSender emailSender;

    @Test
    @DisplayName("❌ Debería lanzar ErrorConfiguracion con cadena de encargados vacía")
    void deberiaLanzarErrorConfiguracionConCadenaVacia() {

    }

    @Test
    @DisplayName("❌ Debería lanzar ErrorConfiguracion con un encargado nulo en la cadena")
    void deberiaLanzarErrorConfiguracionConEncargadoNulo() {

    }

    @Test
    @DisplayName("❌ Debería lanzar ErrorProcesamiento al guardar un prontuario desde una excusa nula")
    void deberiaLanzarErrorProcesamientoConExcusaNula() {
        AdministradorProntuarios admin = AdministradorProntuarios.getInstance();
        ErrorProcesamiento exception = assertThrows(ErrorProcesamiento.class, () -> admin.guardarProntuario(null));
        assertEquals("La excusa no puede ser nula para guardar un prontuario", exception.getMessage());
    }



}
