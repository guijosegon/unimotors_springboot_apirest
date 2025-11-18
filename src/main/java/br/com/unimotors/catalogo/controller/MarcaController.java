package br.com.unimotors.catalogo.controller;

import br.com.unimotors.catalogo.dto.MarcaCadastroDTO;
import br.com.unimotors.catalogo.dto.MarcaRespostaDTO;
import br.com.unimotors.catalogo.service.MarcaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/catalogo/marcas")
public class MarcaController {

    private final MarcaService service;

    public MarcaController(MarcaService service) {
        this.service = service;
    }

    @PostMapping
    public MarcaRespostaDTO criar(@RequestBody @Valid MarcaCadastroDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public MarcaRespostaDTO atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid MarcaCadastroDTO dto
    ) {
        return service.atualizar(id, dto);
    }

    @GetMapping
    public List<MarcaRespostaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MarcaRespostaDTO buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}
