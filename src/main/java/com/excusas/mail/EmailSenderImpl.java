package com.excusas.mail;

import com.excusas.excepciones.ErrorProcesamiento;

public class EmailSenderImpl implements EmailSender {
    
    @Override
    public void enviarEmail(String emailDestino, String emailOrigen, String asunto, String cuerpo) {

        if (emailDestino == null || emailDestino.trim().isEmpty()) {
            throw new ErrorProcesamiento("El email de destino no puede estar vacío");
        }
        
        if (emailOrigen == null || emailOrigen.trim().isEmpty()) {
            throw new ErrorProcesamiento("El email de origen no puede estar vacío");
        }
        
        if (!esEmailValido(emailDestino)) {
            throw new ErrorProcesamiento("El email de destino no tiene un formato válido: " + emailDestino);
        }
        
        if (!esEmailValido(emailOrigen)) {
            throw new ErrorProcesamiento("El email de origen no tiene un formato válido: " + emailOrigen);
        }
        
        try {
            // Simulación de envío de email
            System.out.println("=== EMAIL ENVIADO ===");
            System.out.println("De: " + emailOrigen);
            System.out.println("Para: " + emailDestino);
            System.out.println("Asunto: " + asunto);
            System.out.println("Cuerpo: " + cuerpo);
            System.out.println("====================");
        } catch (Exception e) {
            throw new ErrorProcesamiento("Error al enviar el email", e);
        }
    }
    
    private boolean esEmailValido(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
