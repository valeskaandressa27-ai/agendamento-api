 []
    @PostMapping
    public ResponseEntity<Agendamento> criar(@Valid @RequestBody Agendamento agendamento) {
        Agendamento criado = agendamentoService.criar(agendamento);
        return ResponseEntity.ok(criado);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Agendamento> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.cancelar(id));
    }
}
