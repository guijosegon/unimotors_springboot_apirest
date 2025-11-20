package br.com.unimotors.autenticacao;

import br.com.unimotors.autenticacao.dto.TokenRespostaDTO;
import br.com.unimotors.usuario.dto.UsuarioRespostaDTO;
import br.com.unimotors.autenticacao.jwt.TokenJwtService;
import br.com.unimotors.usuario.dto.LoginDTO;
import br.com.unimotors.usuario.dto.RegistroDTO;
import br.com.unimotors.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autenticacao")
public class AuthController {

    private final UsuarioService usuarios;
    private final TokenJwtService tokens;
    private final AuthenticationManager authManager;

    public AuthController(UsuarioService usuarios, TokenJwtService tokens, AuthenticationManager authManager) {
        this.usuarios = usuarios;
        this.tokens = tokens;
        this.authManager = authManager;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioRespostaDTO> registrar(@RequestBody @Valid RegistroDTO dto) {
        var criado = usuarios.registrar(dto);
        var resposta = new UsuarioRespostaDTO(criado.getId(), criado.getNome(), criado.getEmail(), criado.getPerfil().name(), criado.getTelefone(), criado.getCriadoEm());
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenRespostaDTO> login(@RequestBody @Valid LoginDTO dto) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
        var papeis = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        var token = tokens.gerarToken(dto.email(), papeis);
        return ResponseEntity.ok(new TokenRespostaDTO(token));
    }
}
