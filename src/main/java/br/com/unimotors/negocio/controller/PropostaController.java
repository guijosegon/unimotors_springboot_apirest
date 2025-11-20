package br.com.unimotors.negocio.controller;

import br.com.unimotors.negocio.dto.PropostaCriarDTO;
import br.com.unimotors.negocio.dto.PropostaRespostaDTO;
import br.com.unimotors.negocio.service.PropostaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Proposta")
public class PropostaController {

    private final PropostaService propostas;

    public PropostaController(PropostaService propostas) {
        this.propostas = propostas;
    }

    @PostMapping("/anuncios/{id}/propostas")
    @PreAuthorize("hasRole('COMPRADOR')")
    public ResponseEntity<PropostaRespostaDTO> criar(@PathVariable UUID id,
                                                     @RequestBody @Valid PropostaCriarDTO dto,
                                                     Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(propostas.criar(id, email, dto));
    }

    @GetMapping("/anuncios/{id}/propostas")
    @PreAuthorize("hasAnyRole('VENDEDOR','ADMIN')")
    public ResponseEntity<List<PropostaRespostaDTO>> listar(@PathVariable UUID id, Authentication auth) {
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(propostas.listarPorAnuncio(id, email, isAdmin));
    }

    @PatchMapping("/propostas/{id}/aceitar")
    @PreAuthorize("hasAnyRole('VENDEDOR','ADMIN')")
    public ResponseEntity<PropostaRespostaDTO> aceitar(@PathVariable UUID id, Authentication auth) {
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(propostas.aceitar(id, email, isAdmin));
    }

    @PatchMapping("/propostas/{id}/recusar")
    @PreAuthorize("hasAnyRole('VENDEDOR','ADMIN')")
    public ResponseEntity<PropostaRespostaDTO> recusar(@PathVariable UUID id, Authentication auth) {
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(propostas.recusar(id, email, isAdmin));
    }
}
