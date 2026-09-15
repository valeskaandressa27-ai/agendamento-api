package com.valeska.agendamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O cliente e obrigatorio")
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotNull(message = "O servico e obrigatorio")
    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @NotNull(message = "A data e hora sao obrigatorias")
    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFim;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status = StatusAgendamento.CONFIRMADO;

    public Agendamento() {
    }

    public Agendamento(Cliente cliente, Servico servico, LocalDateTime dataHoraInicio) {
        this.cliente = cliente;
        this.servico = servico;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraInicio.plusMinutes(servico.getDuracaoMinutos());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}
