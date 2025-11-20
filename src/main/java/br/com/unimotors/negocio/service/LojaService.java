package br.com.unimotors.negocio.service;

import br.com.unimotors.negocio.dto.LojaCriarDTO;
import br.com.unimotors.negocio.dto.LojaMembroDTO;
import br.com.unimotors.negocio.dto.LojaRespostaDTO;
import br.com.unimotors.negocio.model.Loja;
import br.com.unimotors.negocio.model.LojaUsuario;
import br.com.unimotors.negocio.model.LojaUsuarioId;
import br.com.unimotors.negocio.repository.LojaRepository;
import br.com.unimotors.negocio.repository.LojaUsuarioRepository;
import br.com.unimotors.usuario.model.Usuario;
import br.com.unimotors.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LojaService {

    private static final Logger log = LoggerFactory.getLogger(LojaService.class);

    private final LojaRepository lojas;
    private final LojaUsuarioRepository lojasUsuarios;
    private final UsuarioRepository usuarios;

    public LojaService(LojaRepository lojas,
                       LojaUsuarioRepository lojasUsuarios,
                       UsuarioRepository usuarios) {
        this.lojas = lojas;
        this.lojasUsuarios = lojasUsuarios;
        this.usuarios = usuarios;
    }

    @Transactional
    public LojaRespostaDTO criar(LojaCriarDTO dto) {
        Loja loja = new Loja(dto.nome(), dto.cnpj(), dto.cidade(), dto.estado());
        Loja salva = lojas.save(loja);
        log.info("Loja criada: {}", salva.getNome());
        return toRespostaDTO(salva);
    }

    @Transactional(readOnly = true)
    public List<LojaRespostaDTO> listar() {
        return lojas.findAll().stream().map(this::toRespostaDTO).toList();
    }

    @Transactional
    public void adicionarMembro(UUID lojaId, LojaMembroDTO dto) {
        Loja loja = lojas.findById(lojaId)
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));
        Usuario usuario = usuarios.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        LojaUsuario vinculo = new LojaUsuario(loja, usuario, dto.papelNaLoja());
        lojasUsuarios.save(vinculo);
        log.info("Usuário {} vinculado à loja {}", usuario.getEmail(), loja.getNome());
    }

    @Transactional
    public void removerMembro(UUID lojaId, UUID usuarioId) {
        LojaUsuarioId id = new LojaUsuarioId(lojaId, usuarioId);
        if (lojasUsuarios.existsById(id)) {
            lojasUsuarios.deleteById(id);
            log.info("Usuário {} desvinculado da loja {}", usuarioId, lojaId);
        }
    }

    private LojaRespostaDTO toRespostaDTO(Loja loja) {
        return new LojaRespostaDTO(
                loja.getId(),
                loja.getNome(),
                loja.getCnpj(),
                loja.getCidade(),
                loja.getEstado()
        );
    }
}

