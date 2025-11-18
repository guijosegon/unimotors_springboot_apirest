package br.com.unimotors.catalogo.controller;

import br.com.unimotors.catalogo.dto.EspecificacaoVeiculoCadastroDTO;
import br.com.unimotors.catalogo.dto.EspecificacaoVeiculoRespostaDTO;
import br.com.unimotors.catalogo.service.EspecificacaoVeiculoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/catalogo/especificacoes")
public class EspecificacaoVeiculoController {

    private final EspecificacaoVeiculoService service;

    public EspecificacaoVeiculoController(EspecificacaoVeiculoService service) {
        this.service = service;
    }

    @PostMapping
    public EspecificacaoVeiculoRespostaDTO criar(@RequestBody @Valid EspecificacaoVeiculoCadastroDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public EspecificacaoVeiculoRespostaDTO atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid EspecificacaoVeiculoCadastroDTO dto
    ) {
        return service.atualizar(id, dto);
    }

    @GetMapping
    public List<EspecificacaoVeiculoRespostaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EspecificacaoVeiculoRespostaDTO buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}
