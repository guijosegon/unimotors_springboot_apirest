package br.com.unimotors.anuncio.controller;

import br.com.unimotors.anuncio.dto.AnuncioCadastroDTO;
import br.com.unimotors.anuncio.dto.AnuncioRespostaDTO;
import br.com.unimotors.anuncio.service.AnuncioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    private final AnuncioService anuncioService;

    public AnuncioController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }

    @PostMapping
    public AnuncioRespostaDTO criar(
            @RequestParam UUID usuarioId, // depois será substituído pelo JWT
            @RequestBody @Valid AnuncioCadastroDTO dto
    ) {
        return anuncioService.criar(usuarioId, dto);
    }

    @GetMapping
    public List<AnuncioRespostaDTO> listar() {
        return anuncioService.listar();
    }

    @GetMapping("/{id}")
    public AnuncioRespostaDTO buscarPorId(@PathVariable UUID id) {
        return anuncioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public AnuncioRespostaDTO atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid AnuncioCadastroDTO dto
    ) {
        return anuncioService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        anuncioService.deletar(id);
    }
}
