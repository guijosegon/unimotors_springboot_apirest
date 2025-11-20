package br.com.unimotors.usuario.service;

import br.com.unimotors.usuario.dto.RegistroDTO;
import br.com.unimotors.usuario.dto.UsuarioAtualizarDTO;
import br.com.unimotors.usuario.dto.UsuarioRespostaDTO;
import br.com.unimotors.usuario.model.Usuario;
import br.com.unimotors.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registrar(RegistroDTO dto) {
        if (repo.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        var usuario = new Usuario(dto.nome(), dto.email(), passwordEncoder.encode(dto.senha()), dto.perfil(), null);
        var salvo = repo.save(usuario);
        log.info("Novo usuário registrado: {}", salvo.getEmail());
        return salvo;
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespostaDTO> listarTodos() {
        return repo.findAll().stream().map(this::toRespostaDTO).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioRespostaDTO buscarPorId(UUID id) {
        var usuario = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return toRespostaDTO(usuario);
    }

    @Transactional
    public Usuario atualizar(UUID id, UsuarioAtualizarDTO dto) {
        var usuario = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setNome(dto.nome());
        usuario.setPerfil(dto.perfil());
        usuario.setTelefone(dto.telefone());
        return repo.save(usuario);
    }

    @Transactional
    public void excluir(UUID id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        repo.deleteById(id);
    }

    public UsuarioRespostaDTO toRespostaDTO(Usuario usuario) {
        return new UsuarioRespostaDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil().name(),
                usuario.getTelefone(),
                usuario.getCriadoEm()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = repo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil().name()));
        return User.withUsername(usuario.getEmail()).password(usuario.getSenhaHash()).authorities(authorities).build();
    }
}
