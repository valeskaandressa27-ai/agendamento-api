package com.valeska.agendamento.service;

import com.valeska.agendamento.exception.ConflitoHorarioException;
import com.valeska.agendamento.model.Agendamento;
import com.valeska.agendamento.model.Cliente;
import com.valeska.agendamento.model.Servico;
import com.valeska.agendamento.model.StatusAgendamento;
import com.valeska.agendamento.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private Cliente cliente;
    private Servico servico;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Ana Souza", "81999990000", "ana@email.com");
        servico = new Servico("Corte de cabelo", 60, 80.0);
    }

    @Test
    void deveCriarAgendamentoQuandoNaoHaConflito() {
        LocalDateTime inicio = LocalDateTime.of(2026, 9, 20, 10, 0);
        Agendamento novoAgendamento = new Agendamento(cliente, servico, inicio);

        when(agendamentoRepository.buscarConflitos(any(), any(), eq(StatusAgendamento.CANCELADO)))
                .thenReturn(Collections.emptyList());
        when(agendamentoRepository.save(novoAgendamento)).thenReturn(novoAgendamento);

        Agendamento resultado = agendamentoService.criar(novoAgendamento);

        assertNotNull(resultado);
        assertEquals(StatusAgendamento.CONFIRMADO, resultado.getStatus());
        verify(agendamentoRepository, times(1)).save(novoAgendamento);
    }

    @Test
    void deveLancarExcecaoQuandoHaConflitoDeHorario() {
        LocalDateTime inicio = LocalDateTime.of(2026, 9, 20, 10, 0);
        Agendamento novoAgendamento = new Agendamento(cliente, servico, inicio);
        Agendamento agendamentoExistente = new Agendamento(cliente, servico, inicio);

        when(agendamentoRepository.buscarConflitos(any(), any(), eq(StatusAgendamento.CANCELADO)))
                .thenReturn(List.of(agendamentoExistente));

        assertThrows(ConflitoHorarioException.class, () -> agendamentoService.criar(novoAgendamento));
        verify(agendamentoRepository, never()).save(any());
    }

    @Test
    void deveCalcularDataHoraFimComBaseNaDuracaoDoServico() {
        LocalDateTime inicio = LocalDateTime.of(2026, 9, 20, 14, 0);
        Agendamento agendamento = new Agendamento(cliente, servico, inicio);

        LocalDateTime fimEsperado = inicio.plusMinutes(servico.getDuracaoMinutos());

        assertEquals(fimEsperado, agendamento.getDataHoraFim());
    }

    @Test
    void deveCancelarAgendamentoExistente() {
        Agendamento agendamento = new Agendamento(cliente, servico, LocalDateTime.now());
        agendamento.setId(1L);

        when(agendamentoRepository.findById(1L)).thenReturn(java.util.Optional.of(agendamento));
        when(agendamentoRepository.save(any())).thenReturn(agendamento);

        Agendamento cancelado = agendamentoService.cancelar(1L);

        assertEquals(StatusAgendamento.CANCELADO, cancelado.getStatus());
    }

    @Test
    void deveLancarExcecaoAoBuscarAgendamentoInexistente() {
        when(agendamentoRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> agendamentoService.buscarPorId(99L));
    }
}
