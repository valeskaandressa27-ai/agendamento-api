package com.valeska.agendamento.exception;

public class ConflitoHorarioException extends RuntimeException {

    public ConflitoHorarioException(String mensagem) {
        super(mensagem);
    }
}
