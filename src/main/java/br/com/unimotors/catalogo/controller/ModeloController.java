package br.com.unimotors.catalogo.controller;

import br.com.unimotors.catalogo.dto.ModeloCadastroDTO;
import br.com.unimotors.catalogo.dto.ModeloRespostaDTO;
import br.com.unimotors.catalogo.service.ModeloService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/catalogo/modelos")
public class ModeloController {

    private final ModeloService service;

    public ModeloController(ModeloService service) {
        this.service = service;
    }

    @PostMapping
    public ModeloRespostaDTO criar(@RequestBody @Valid ModeloCadastroDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public ModeloRespostaDTO atualizar(@PathVariable UUID id, @RequestBody @Valid ModeloCadastroDTO dto) {
        return service.atualizar(id, dto);
    }

    @GetMapping
    public List<ModeloRespostaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ModeloRespostaDTO buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}
