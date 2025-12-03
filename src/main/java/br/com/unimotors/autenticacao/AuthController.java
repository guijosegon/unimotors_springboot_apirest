package br.com.unimotors.autenticacao;

import br.com.unimotors.autenticacao.dto.TokenRespostaDTO;
import br.com.unimotors.usuario.dto.UsuarioRespostaDTO;
import br.com.unimotors.autenticacao.jwt.TokenJwtService;
import br.com.unimotors.usuario.dto.LoginDTO;
import br.com.unimotors.usuario.dto.RegistroDTO;
import br.com.unimotors.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autenticacao")
@Tag(name = "Auth")
public class AuthController {

    private final UsuarioService usuarios;
    private final TokenJwtService tokens;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarios, TokenJwtService tokens, AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
        this.usuarios = usuarios;
        this.tokens = tokens;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioRespostaDTO> registrar(@RequestBody @Valid RegistroDTO dto) {
        var criado = usuarios.registrar(dto);
        var resposta = new UsuarioRespostaDTO(criado.getId(), criado.getNome(), criado.getEmail(), criado.getPerfil().name(), criado.getTelefone(), criado.getCriadoEm());
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto) {
        try {
            UserDetails user = usuarios.loadUserByUsername(dto.email());
            if (!passwordEncoder.matches(dto.senha(), user.getPassword())) {
                throw new BadCredentialsException("E-mail ou senha inválidos");
            }
            var papeis = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            var token = tokens.gerarToken(user.getUsername(), papeis);
            return ResponseEntity.ok(new TokenRespostaDTO(token));
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            var body = java.util.Map.of(
                    "status", 401,
                    "erro", "Não autorizado",
                    "mensagem", ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }
    }
}
