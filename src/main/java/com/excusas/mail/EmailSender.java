package com.excusas.mail;

public interface EmailSender {
    void enviarEmail(String emailDestino, String emailOrigen, String asunto, String cuerpo);
}
