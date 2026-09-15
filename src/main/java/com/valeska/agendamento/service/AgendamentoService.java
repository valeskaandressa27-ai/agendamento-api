package com.valeska.agendamento.service;

import com.valeska.agendamento.exception.ConflitoHorarioException;
import com.valeska.agendamento.model.Agendamento;
import com.valeska.agendamento.model.StatusAgendamento;
import com.valeska.agendamento.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento nao encontrado: " + id));
    }

    /**
     * Cria um novo agendamento, validando que nao ha conflito de horario
     * com outro agendamento ativo (nao cancelado).
     */
    public Agendamento criar(Agendamento novoAgendamento) {
        validarConflito(novoAgendamento);
        return agendamentoRepository.save(novoAgendamento);
    }

    public Agendamento cancelar(Long id) {
        Agendamento agendamento = buscarPorId(id);
        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return agendamentoRepository.save(agendamento);
    }

    private void validarConflito(Agendamento novoAgendamento) {
        List<Agendamento> conflitos = agendamentoRepository.buscarConflitos(
                novoAgendamento.getDataHoraInicio(),
                novoAgendamento.getDataHoraFim(),
                StatusAgendamento.CANCELADO
        );

        if (!conflitos.isEmpty()) {
            throw new ConflitoHorarioException(
                    "Ja existe um agendamento nesse intervalo de horario. " +
                    "Escolha outro horario disponivel."
            );
        }
    }
}
