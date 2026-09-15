package com.valeska.agendamento.controller;

import com.valeska.agendamento.model.Servico;
import com.valeska.agendamento.repository.ServicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping
    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        return servicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Servico> criar(@Valid @RequestBody Servico servico) {
        return ResponseEntity.ok(servicoRepository.save(servico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!servicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
