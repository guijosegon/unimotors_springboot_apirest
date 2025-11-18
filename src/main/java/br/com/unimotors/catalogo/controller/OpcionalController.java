package br.com.unimotors.catalogo.controller;

import br.com.unimotors.catalogo.dto.OpcionalCadastroDTO;
import br.com.unimotors.catalogo.dto.OpcionalRespostaDTO;
import br.com.unimotors.catalogo.service.OpcionalService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/catalogo/opcionais")
public class OpcionalController {

    private final OpcionalService service;

    public OpcionalController(OpcionalService service) {
        this.service = service;
    }

    @PostMapping
    public OpcionalRespostaDTO criar(@RequestBody @Valid OpcionalCadastroDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public OpcionalRespostaDTO atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid OpcionalCadastroDTO dto
    ) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }

    @GetMapping
    public List<OpcionalRespostaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public OpcionalRespostaDTO buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }
}
