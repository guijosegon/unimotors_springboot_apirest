package br.com.unimotors.anuncio.controller;

import br.com.unimotors.anuncio.dto.ImagemAnuncioCadastroDTO;
import br.com.unimotors.anuncio.dto.ImagemAnuncioRespostaDTO;
import br.com.unimotors.anuncio.service.ImagemAnuncioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/anuncios/imagens")
public class ImagemAnuncioController {

    private final ImagemAnuncioService imagemService;

    public ImagemAnuncioController(ImagemAnuncioService imagemService) {
        this.imagemService = imagemService;
    }

    @PostMapping
    public ImagemAnuncioRespostaDTO adicionar(@RequestBody @Valid ImagemAnuncioCadastroDTO dto) {
        return imagemService.adicionar(dto);
    }

    @GetMapping("/{anuncioId}")
    public List<ImagemAnuncioRespostaDTO> listar(@PathVariable UUID anuncioId) {
        return imagemService.listarPorAnuncio(anuncioId);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable UUID id) {
        imagemService.remover(id);
    }
}
