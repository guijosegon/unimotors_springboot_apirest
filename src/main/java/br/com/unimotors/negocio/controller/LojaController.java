package br.com.unimotors.negocio.controller;

import br.com.unimotors.negocio.dto.LojaCriarDTO;
import br.com.unimotors.negocio.dto.LojaMembroDTO;
import br.com.unimotors.negocio.dto.LojaRespostaDTO;
import br.com.unimotors.negocio.service.LojaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lojas")
@Tag(name = "Loja")
public class LojaController {

    private final LojaService lojas;

    public LojaController(LojaService lojas) {
        this.lojas = lojas;
    }

    @GetMapping
    public ResponseEntity<List<LojaRespostaDTO>> listar() {
        return ResponseEntity.ok(lojas.listar());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LojaRespostaDTO> criar(@RequestBody @Valid LojaCriarDTO dto) {
        return ResponseEntity.ok(lojas.criar(dto));
    }

    @PostMapping("/{id}/membros")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> adicionarMembro(@PathVariable UUID id, @RequestBody @Valid LojaMembroDTO dto) {
        lojas.adicionarMembro(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/membros/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removerMembro(@PathVariable UUID id, @PathVariable UUID usuarioId) {
        lojas.removerMembro(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
