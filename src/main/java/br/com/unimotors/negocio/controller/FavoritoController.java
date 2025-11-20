package br.com.unimotors.negocio.controller;

import br.com.unimotors.anuncio.dto.AnuncioRespostaDTO;
import br.com.unimotors.negocio.service.FavoritoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Favorito")
public class FavoritoController {

    private final FavoritoService favoritos;

    public FavoritoController(FavoritoService favoritos) {
        this.favoritos = favoritos;
    }

    @PostMapping("/anuncios/{id}/favoritos")
    public ResponseEntity<Void> favoritar(@PathVariable UUID id, Authentication auth) {
        favoritos.favoritar(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/anuncios/{id}/favoritos")
    public ResponseEntity<Void> desfavoritar(@PathVariable UUID id, Authentication auth) {
        favoritos.desfavoritar(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/meus/favoritos")
    public ResponseEntity<List<AnuncioRespostaDTO>> listar(Authentication auth) {
        return ResponseEntity.ok(favoritos.listarFavoritos(auth.getName()));
    }
}
