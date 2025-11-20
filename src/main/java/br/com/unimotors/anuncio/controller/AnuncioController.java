package br.com.unimotors.anuncio.controller;

import br.com.unimotors.anuncio.dto.AnuncioAtualizarDTO;
import br.com.unimotors.anuncio.dto.AnuncioCriarDTO;
import br.com.unimotors.anuncio.dto.AnuncioRespostaDTO;
import br.com.unimotors.anuncio.dto.AtualizarStatusAnuncioDTO;
import br.com.unimotors.anuncio.service.AnuncioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/anuncios")
@Tag(name = "Anuncio")
public class AnuncioController {

    private final AnuncioService anuncios;

    public AnuncioController(AnuncioService anuncios) {
        this.anuncios = anuncios;
    }

    @GetMapping
    public ResponseEntity<Page<AnuncioRespostaDTO>> buscar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) String cidade,
            Pageable pageable) {
        return ResponseEntity.ok(anuncios.buscar(marca, modelo, precoMin, cidade, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnuncioRespostaDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(anuncios.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDEDOR','ADMIN')")
    public ResponseEntity<AnuncioRespostaDTO> criar(@RequestBody @Valid AnuncioCriarDTO dto, Authentication auth) {
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        // para criação, apenas checagem por role já foi feita em @PreAuthorize
        return ResponseEntity.ok(anuncios.criar(email, dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('VENDEDOR','ADMIN')")
    public ResponseEntity<AnuncioRespostaDTO> atualizar(@PathVariable UUID id,
                                                        @RequestBody @Valid AnuncioAtualizarDTO dto,
                                                        Authentication auth) {
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(anuncios.atualizar(email, isAdmin, id, dto));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('VENDEDOR','ADMIN')")
    public ResponseEntity<AnuncioRespostaDTO> atualizarStatus(@PathVariable UUID id,
                                                              @RequestBody @Valid AtualizarStatusAnuncioDTO dto,
                                                              Authentication auth) {
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(anuncios.atualizarStatus(email, isAdmin, id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('VENDEDOR','ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable UUID id, Authentication auth) {
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        anuncios.excluir(email, isAdmin, id);
        return ResponseEntity.noContent().build();
    }
}
