package com.valeska.agendamento.repository;

import com.valeska.agendamento.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
