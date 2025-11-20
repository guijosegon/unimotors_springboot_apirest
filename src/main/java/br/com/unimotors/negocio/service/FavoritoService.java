package br.com.unimotors.negocio.service;

import br.com.unimotors.anuncio.dto.AnuncioRespostaDTO;
import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import br.com.unimotors.anuncio.repository.AnuncioRepository;
import br.com.unimotors.anuncio.service.AnuncioService;
import br.com.unimotors.negocio.model.Favorito;
import br.com.unimotors.negocio.model.FavoritoId;
import br.com.unimotors.negocio.repository.FavoritoRepository;
import br.com.unimotors.usuario.model.Usuario;
import br.com.unimotors.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoritoService {

    private static final Logger log = LoggerFactory.getLogger(FavoritoService.class);

    private final FavoritoRepository favoritos;
    private final UsuarioRepository usuarios;
    private final AnuncioRepository anuncios;
    private final AnuncioService anuncioService;

    public FavoritoService(FavoritoRepository favoritos,
                           UsuarioRepository usuarios,
                           AnuncioRepository anuncios,
                           AnuncioService anuncioService) {
        this.favoritos = favoritos;
        this.usuarios = usuarios;
        this.anuncios = anuncios;
        this.anuncioService = anuncioService;
    }

    @Transactional
    public void favoritar(String emailUsuario, java.util.UUID anuncioId) {
        Usuario usuario = usuarios.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        AnuncioVeiculo anuncio = anuncios.findById(anuncioId)
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));

        FavoritoId id = new FavoritoId(usuario.getId(), anuncio.getId());
        if (favoritos.existsById(id)) {
            return; // já favoritado, não faz nada
        }
        Favorito favorito = new Favorito(usuario, anuncio);
        favoritos.save(favorito);
        log.info("Anuncio {} favoritado por {}", anuncioId, emailUsuario);
    }

    @Transactional
    public void desfavoritar(String emailUsuario, java.util.UUID anuncioId) {
        Usuario usuario = usuarios.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        FavoritoId id = new FavoritoId(usuario.getId(), anuncioId);
        if (favoritos.existsById(id)) {
            favoritos.deleteById(id);
            log.info("Anuncio {} removido dos favoritos de {}", anuncioId, emailUsuario);
        }
    }

    @Transactional(readOnly = true)
    public List<AnuncioRespostaDTO> listarFavoritos(String emailUsuario) {
        Usuario usuario = usuarios.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return favoritos.findByUsuarioId(usuario.getId()).stream()
                .map(Favorito::getAnuncio)
                .map(anuncioService::toResposta)
                .toList();
    }
}

