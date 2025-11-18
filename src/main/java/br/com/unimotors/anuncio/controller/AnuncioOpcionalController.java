package br.com.unimotors.anuncio.controller;

import br.com.unimotors.anuncio.dto.AnuncioOpcionalCadastroDTO;
import br.com.unimotors.anuncio.dto.AnuncioOpcionalRespostaDTO;
import br.com.unimotors.anuncio.service.AnuncioOpcionalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/anuncios/opcionais")
public class AnuncioOpcionalController {

    private final AnuncioOpcionalService service;

    public AnuncioOpcionalController(AnuncioOpcionalService service) {
        this.service = service;
    }

    @PostMapping
    public AnuncioOpcionalRespostaDTO adicionar(
            @RequestBody @Valid AnuncioOpcionalCadastroDTO dto
    ) {
        return service.adicionar(dto);
    }

    @GetMapping("/{anuncioId}")
    public List<AnuncioOpcionalRespostaDTO> listar(@PathVariable UUID anuncioId) {
        return service.listarPorAnuncio(anuncioId);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable UUID id) {
        service.remover(id);
    }
}
