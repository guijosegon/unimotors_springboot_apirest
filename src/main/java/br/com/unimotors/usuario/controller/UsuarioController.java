package br.com.unimotors.usuario.controller;

import br.com.unimotors.usuario.dto.RegistroDTO;
import br.com.unimotors.usuario.dto.UsuarioAtualizarDTO;
import br.com.unimotors.usuario.dto.UsuarioRespostaDTO;
import br.com.unimotors.usuario.model.Usuario;
import br.com.unimotors.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Usuario")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarios;

    public UsuarioController(UsuarioService usuarios) {
        this.usuarios = usuarios;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRespostaDTO>> listar() {
        List<UsuarioRespostaDTO> lista = usuarios.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> buscarPorId(@PathVariable UUID id) {
        UsuarioRespostaDTO usuario = usuarios.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioRespostaDTO> criar(@RequestBody @Valid RegistroDTO dto) {
        Usuario criado = usuarios.registrar(dto);
        log.info("Usuário criado via ADMIN: {}", criado.getEmail());
        return ResponseEntity.ok(usuarios.toRespostaDTO(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizar(@PathVariable UUID id, @RequestBody @Valid UsuarioAtualizarDTO dto) {
        Usuario atualizado = usuarios.atualizar(id, dto);
        log.info("Usuário atualizado: {}", atualizado.getEmail());
        return ResponseEntity.ok(usuarios.toRespostaDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        usuarios.excluir(id);
        log.info("Usuário excluído: {}", id);
        return ResponseEntity.noContent().build();
    }
}
