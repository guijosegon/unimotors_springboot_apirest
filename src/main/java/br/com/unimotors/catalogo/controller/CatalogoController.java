package br.com.unimotors.catalogo.controller;

import br.com.unimotors.catalogo.dto.*;
import br.com.unimotors.catalogo.service.CatalogoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CatalogoController {

    private final CatalogoService catalogo;

    public CatalogoController(CatalogoService catalogo) {
        this.catalogo = catalogo;
    }

    @GetMapping("/marcas")
    public ResponseEntity<List<MarcaRespostaDTO>> listarMarcas() {
        return ResponseEntity.ok(catalogo.listarMarcas());
    }

    @PostMapping("/marcas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarcaRespostaDTO> criarMarca(@RequestBody @Valid MarcaCriarDTO dto) {
        return ResponseEntity.ok(catalogo.criarMarca(dto));
    }

    @GetMapping("/marcas/{id}/modelos")
    public ResponseEntity<List<ModeloRespostaDTO>> listarModelosPorMarca(@PathVariable UUID id) {
        return ResponseEntity.ok(catalogo.listarModelosPorMarca(id));
    }

    @PostMapping("/modelos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModeloRespostaDTO> criarModelo(@RequestBody @Valid ModeloCriarDTO dto) {
        return ResponseEntity.ok(catalogo.criarModelo(dto));
    }

    @GetMapping("/modelos/{id}/especificacoes")
    public ResponseEntity<List<EspecificacaoVeiculoRespostaDTO>> listarEspecificacoes(@PathVariable UUID id) {
        return ResponseEntity.ok(catalogo.listarEspecificacoesPorModelo(id));
    }

    @PostMapping("/especificacoes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EspecificacaoVeiculoRespostaDTO> criarEspecificacao(@RequestBody @Valid EspecificacaoVeiculoCriarDTO dto) {
        return ResponseEntity.ok(catalogo.criarEspecificacao(dto));
    }

    @GetMapping("/opcionais")
    public ResponseEntity<List<OpcionalRespostaDTO>> listarOpcionais() {
        return ResponseEntity.ok(catalogo.listarOpcionais());
    }

    @PostMapping("/opcionais")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OpcionalRespostaDTO> criarOpcional(@RequestBody @Valid OpcionalCriarDTO dto) {
        return ResponseEntity.ok(catalogo.criarOpcional(dto));
    }
}

