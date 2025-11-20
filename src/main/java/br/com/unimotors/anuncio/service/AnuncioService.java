package br.com.unimotors.anuncio.service;

import br.com.unimotors.anuncio.dto.AnuncioAtualizarDTO;
import br.com.unimotors.anuncio.dto.AnuncioCriarDTO;
import br.com.unimotors.anuncio.dto.AnuncioRespostaDTO;
import br.com.unimotors.anuncio.dto.AtualizarStatusAnuncioDTO;
import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import br.com.unimotors.anuncio.model.ImagemAnuncio;
import br.com.unimotors.anuncio.model.StatusAnuncio;
import br.com.unimotors.anuncio.repository.AnuncioRepository;
import br.com.unimotors.anuncio.repository.ImagemAnuncioRepository;
import br.com.unimotors.catalogo.model.Opcional;
import br.com.unimotors.catalogo.repository.EspecificacaoVeiculoRepository;
import br.com.unimotors.catalogo.repository.ModeloRepository;
import br.com.unimotors.catalogo.repository.OpcionalRepository;
import br.com.unimotors.usuario.model.Usuario;
import br.com.unimotors.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AnuncioService {

    private static final Logger log = LoggerFactory.getLogger(AnuncioService.class);

    private final AnuncioRepository anuncios;
    private final ImagemAnuncioRepository imagens;
    private final UsuarioRepository usuarios;
    private final ModeloRepository modelos;
    private final EspecificacaoVeiculoRepository especificacoes;
    private final OpcionalRepository opcionais;

    public AnuncioService(AnuncioRepository anuncios,
                          ImagemAnuncioRepository imagens,
                          UsuarioRepository usuarios,
                          ModeloRepository modelos,
                          EspecificacaoVeiculoRepository especificacoes,
                          OpcionalRepository opcionais) {
        this.anuncios = anuncios;
        this.imagens = imagens;
        this.usuarios = usuarios;
        this.modelos = modelos;
        this.especificacoes = especificacoes;
        this.opcionais = opcionais;
    }

    @Transactional
    public AnuncioRespostaDTO criar(String emailUsuario, AnuncioCriarDTO dto) {
        Usuario proprietario = usuarios.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        var modelo = modelos.findById(dto.modeloId())
                .orElseThrow(() -> new EntityNotFoundException("Modelo não encontrado"));

        if (dto.preco() == null || dto.preco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        var anuncio = new AnuncioVeiculo(proprietario, modelo, dto.titulo(), dto.preco(), dto.cidade(), dto.estado());
        anuncio.setDescricao(dto.descricao());

        if (dto.especificacaoId() != null) {
            var esp = especificacoes.findById(dto.especificacaoId())
                    .orElseThrow(() -> new EntityNotFoundException("Especificação não encontrada"));
            anuncio.setEspecificacao(esp);
        }

        if (dto.opcionaisIds() != null && !dto.opcionaisIds().isEmpty()) {
            List<Opcional> lista = opcionais.findAllById(dto.opcionaisIds());
            anuncio.setOpcionais(new HashSet<>(lista));
        }

        AnuncioVeiculo salvo = anuncios.save(anuncio);

        if (dto.urlsImagens() != null && !dto.urlsImagens().isEmpty()) {
            int ordem = 0;
            for (String url : dto.urlsImagens()) {
                ImagemAnuncio img = new ImagemAnuncio(salvo, url, ordem++);
                imagens.save(img);
                salvo.getImagens().add(img);
            }
        }

        log.info("Anúncio criado: {} por {}", salvo.getTitulo(), proprietario.getEmail());
        return toRespostaDTO(salvo);
    }

    @Transactional(readOnly = true)
    public Page<AnuncioRespostaDTO> buscar(String marca, String modelo, BigDecimal precoMin, String cidade, Pageable pageable) {
        Page<AnuncioVeiculo> page = anuncios.buscarPorFiltro(
                vazioParaNull(marca),
                vazioParaNull(modelo),
                precoMin,
                vazioParaNull(cidade),
                pageable
        );
        return page.map(this::toRespostaDTO);
    }

    @Transactional(readOnly = true)
    public AnuncioRespostaDTO buscarPorId(UUID id) {
        var anuncio = anuncios.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));
        return toRespostaDTO(anuncio);
    }

    @Transactional
    public AnuncioRespostaDTO atualizar(String emailUsuario, boolean isAdmin, UUID id, AnuncioAtualizarDTO dto) {
        var anuncio = anuncios.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));
        garantirProprietarioOuAdmin(emailUsuario, isAdmin, anuncio);

        var modelo = modelos.findById(dto.modeloId())
                .orElseThrow(() -> new EntityNotFoundException("Modelo não encontrado"));

        if (dto.preco() == null || dto.preco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        anuncio.setModelo(modelo);
        anuncio.setTitulo(dto.titulo());
        anuncio.setDescricao(dto.descricao());
        anuncio.setPreco(dto.preco());
        anuncio.setCidade(dto.cidade());
        anuncio.setEstado(dto.estado());

        if (dto.especificacaoId() != null) {
            var esp = especificacoes.findById(dto.especificacaoId())
                    .orElseThrow(() -> new EntityNotFoundException("Especificação não encontrada"));
            anuncio.setEspecificacao(esp);
        } else {
            anuncio.setEspecificacao(null);
        }

        Set<Opcional> novosOpcionais = Collections.emptySet();
        if (dto.opcionaisIds() != null && !dto.opcionaisIds().isEmpty()) {
            novosOpcionais = new HashSet<>(opcionais.findAllById(dto.opcionaisIds()));
        }
        anuncio.setOpcionais(novosOpcionais);

        // Atualizar imagens: remover existentes e criar novas
        imagens.deleteAll(anuncio.getImagens());
        anuncio.getImagens().clear();
        if (dto.urlsImagens() != null && !dto.urlsImagens().isEmpty()) {
            int ordem = 0;
            for (String url : dto.urlsImagens()) {
                ImagemAnuncio img = new ImagemAnuncio(anuncio, url, ordem++);
                imagens.save(img);
                anuncio.getImagens().add(img);
            }
        }

        return toRespostaDTO(anuncio);
    }

    @Transactional
    public AnuncioRespostaDTO atualizarStatus(String emailUsuario, boolean isAdmin, UUID id, AtualizarStatusAnuncioDTO dto) {
        var anuncio = anuncios.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));
        garantirProprietarioOuAdmin(emailUsuario, isAdmin, anuncio);

        StatusAnuncio novoStatus = dto.status();
        if (novoStatus == StatusAnuncio.ATIVO) {
            if (anuncio.getPreco() == null || anuncio.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Anúncio ativo deve ter preço maior que zero");
            }
            if (anuncio.getImagens() == null || anuncio.getImagens().isEmpty()) {
                throw new IllegalArgumentException("Anúncio ativo deve possuir ao menos uma imagem");
            }
        }

        anuncio.setStatus(novoStatus);
        return toRespostaDTO(anuncio);
    }

    @Transactional
    public void excluir(String emailUsuario, boolean isAdmin, UUID id) {
        var anuncio = anuncios.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));
        garantirProprietarioOuAdmin(emailUsuario, isAdmin, anuncio);
        anuncios.delete(anuncio);
        log.info("Anúncio excluído: {}", id);
    }

    private void garantirProprietarioOuAdmin(String emailUsuario, boolean isAdmin, AnuncioVeiculo anuncio) {
        if (!isAdmin && !anuncio.getProprietario().getEmail().equalsIgnoreCase(emailUsuario)) {
            throw new IllegalArgumentException("Usuário não é proprietário do anúncio");
        }
    }

    private String vazioParaNull(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor;
    }

    public AnuncioRespostaDTO toResposta(AnuncioVeiculo a) {
        String marca = a.getModelo() != null && a.getModelo().getMarca() != null ? a.getModelo().getMarca().getNome() : null;
        String modelo = a.getModelo() != null ? a.getModelo().getNome() : null;
        Integer ano = a.getEspecificacao() != null ? a.getEspecificacao().getAno() : null;
        List<String> imagensUrls = a.getImagens() != null
                ? a.getImagens().stream().map(ImagemAnuncio::getUrl).toList()
                : List.of();
        Set<String> opcionaisNomes = a.getOpcionais() != null
                ? a.getOpcionais().stream().map(o -> o.getNome()).collect(java.util.stream.Collectors.toSet())
                : Set.of();

        return new AnuncioRespostaDTO(
                a.getId(),
                a.getTitulo(),
                a.getDescricao(),
                a.getPreco(),
                a.getStatus() != null ? a.getStatus().name() : null,
                a.getCidade(),
                a.getEstado(),
                marca,
                modelo,
                ano,
                imagensUrls,
                opcionaisNomes,
                a.getProprietario() != null ? a.getProprietario().getId() : null
        );
    }

    private AnuncioRespostaDTO toRespostaDTO(AnuncioVeiculo a) {
        return toResposta(a);
    }
}
