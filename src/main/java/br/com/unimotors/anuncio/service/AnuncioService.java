package br.com.unimotors.anuncio.service;

import br.com.unimotors.anuncio.dto.AnuncioCadastroDTO;
import br.com.unimotors.anuncio.dto.AnuncioRespostaDTO;
import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import br.com.unimotors.anuncio.repository.AnuncioRepository;
import br.com.unimotors.catalogo.model.Modelo;
import br.com.unimotors.catalogo.repository.ModeloRepository;
import br.com.unimotors.usuario.model.Usuario;
import br.com.unimotors.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.Set;

@Service
public class AnuncioService {

    private final AnuncioRepository anuncioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModeloRepository modeloRepository;

    public AnuncioService(
            AnuncioRepository anuncioRepository,
            UsuarioRepository usuarioRepository,
            ModeloRepository modeloRepository
    ) {
        this.anuncioRepository = anuncioRepository;
        this.usuarioRepository = usuarioRepository;
        this.modeloRepository = modeloRepository;
    }

    @Transactional
    public AnuncioRespostaDTO criar(UUID usuarioId, AnuncioCadastroDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Modelo modelo = modeloRepository.findById(dto.modeloId())
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado."));

        AnuncioVeiculo anuncio = new AnuncioVeiculo(
                usuario,
                modelo,
                dto.titulo(),
                dto.preco(),
                dto.cidade(),
                dto.estado(),
                dto.ano()
        );

        anuncio.setDescricao(dto.descricao());

        anuncioRepository.save(anuncio);

        return converterParaResposta(anuncio);
    }

    public List<AnuncioRespostaDTO> listar() {
        return anuncioRepository.findAll()
                .stream()
                .map(this::converterParaResposta)
                .toList();
    }

    public AnuncioRespostaDTO buscarPorId(UUID id) {
        var anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado."));
        return converterParaResposta(anuncio);
    }

    @Transactional
    public AnuncioRespostaDTO atualizar(UUID id, AnuncioCadastroDTO dto) {
        var anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado."));

        Modelo modelo = modeloRepository.findById(dto.modeloId())
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado."));

        anuncio.setTitulo(dto.titulo());
        anuncio.setDescricao(dto.descricao());
        anuncio.setPreco(dto.preco());
        anuncio.setCidade(dto.cidade());
        anuncio.setEstado(dto.estado());
        anuncio.setModelo(modelo);

        return converterParaResposta(anuncio);
    }

    public void deletar(UUID id) {
        if (!anuncioRepository.existsById(id)) {
            throw new RuntimeException("Anúncio não encontrado.");
        }
        anuncioRepository.deleteById(id);
    }

    private AnuncioRespostaDTO converterParaResposta(AnuncioVeiculo a) {
        return new AnuncioRespostaDTO(
                a.getId(),
                a.getTitulo(),
                a.getDescricao(),
                a.getPreco(),
                a.getStatus(),
                a.getCidade(),
                a.getEstado(),
                a.getModelo().getMarca().getNome(),
                a.getModelo().getNome(),
                a.getAno(),
                List.of(),
                Set.of(),
                a.getProprietario().getId()
        );
    }
}
