package com.valeska.agendamento.repository;

import com.valeska.agendamento.model.Agendamento;
import com.valeska.agendamento.model.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Busca agendamentos que se sobrepoem ao intervalo informado e ainda estao ativos (nao cancelados)
    @Query("SELECT a FROM Agendamento a " +
           "WHERE a.status <> :statusCancelado " +
           "AND a.dataHoraInicio < :fim " +
           "AND a.dataHoraFim > :inicio")
    List<Agendamento> buscarConflitos(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            @Param("statusCancelado") StatusAgendamento statusCancelado
    );
}
