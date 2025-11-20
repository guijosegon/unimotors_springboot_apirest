package br.com.unimotors.catalogo.service;

import br.com.unimotors.catalogo.dto.*;
import br.com.unimotors.catalogo.model.EspecificacaoVeiculo;
import br.com.unimotors.catalogo.model.Marca;
import br.com.unimotors.catalogo.model.Modelo;
import br.com.unimotors.catalogo.model.Opcional;
import br.com.unimotors.catalogo.repository.EspecificacaoVeiculoRepository;
import br.com.unimotors.catalogo.repository.MarcaRepository;
import br.com.unimotors.catalogo.repository.ModeloRepository;
import br.com.unimotors.catalogo.repository.OpcionalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CatalogoService {

    private static final Logger log = LoggerFactory.getLogger(CatalogoService.class);

    private final MarcaRepository marcas;
    private final ModeloRepository modelos;
    private final EspecificacaoVeiculoRepository especificacoes;
    private final OpcionalRepository opcionais;

    public CatalogoService(MarcaRepository marcas,
                           ModeloRepository modelos,
                           EspecificacaoVeiculoRepository especificacoes,
                           OpcionalRepository opcionais) {
        this.marcas = marcas;
        this.modelos = modelos;
        this.especificacoes = especificacoes;
        this.opcionais = opcionais;
    }

    @Transactional(readOnly = true)
    public List<MarcaRespostaDTO> listarMarcas() {
        return marcas.findAll().stream()
                .map(m -> new MarcaRespostaDTO(m.getId(), m.getNome()))
                .toList();
    }

    @Transactional
    public MarcaRespostaDTO criarMarca(MarcaCriarDTO dto) {
        var marca = new Marca(dto.nome());
        var salva = marcas.save(marca);
        log.info("Marca criada: {}", salva.getNome());
        return new MarcaRespostaDTO(salva.getId(), salva.getNome());
    }

    @Transactional(readOnly = true)
    public List<ModeloRespostaDTO> listarModelosPorMarca(UUID marcaId) {
        return modelos.findByMarcaId(marcaId).stream()
                .map(m -> new ModeloRespostaDTO(m.getId(), m.getNome(), m.getMarca().getNome()))
                .toList();
    }

    @Transactional
    public ModeloRespostaDTO criarModelo(ModeloCriarDTO dto) {
        Marca marca = marcas.findById(dto.marcaId())
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));
        Modelo modelo = new Modelo(marca, dto.nome());
        Modelo salvo = modelos.save(modelo);
        log.info("Modelo criado: {} / {}", salvo.getMarca().getNome(), salvo.getNome());
        return new ModeloRespostaDTO(salvo.getId(), salvo.getNome(), salvo.getMarca().getNome());
    }

    @Transactional(readOnly = true)
    public List<EspecificacaoVeiculoRespostaDTO> listarEspecificacoesPorModelo(UUID modeloId) {
        return especificacoes.findByModeloId(modeloId).stream()
                .map(this::toEspecificacaoResposta)
                .toList();
    }

    @Transactional
    public EspecificacaoVeiculoRespostaDTO criarEspecificacao(EspecificacaoVeiculoCriarDTO dto) {
        Modelo modelo = modelos.findById(dto.modeloId())
                .orElseThrow(() -> new EntityNotFoundException("Modelo não encontrado"));
        EspecificacaoVeiculo esp = new EspecificacaoVeiculo(
                modelo,
                dto.ano(),
                dto.combustivel(),
                dto.cambio(),
                dto.carroceria(),
                dto.portas()
        );
        EspecificacaoVeiculo salvo = especificacoes.save(esp);
        log.info("Especificação criada para modelo {}", modelo.getNome());
        return toEspecificacaoResposta(salvo);
    }

    @Transactional(readOnly = true)
    public List<OpcionalRespostaDTO> listarOpcionais() {
        return opcionais.findAll().stream()
                .map(o -> new OpcionalRespostaDTO(o.getId(), o.getNome()))
                .toList();
    }

    @Transactional
    public OpcionalRespostaDTO criarOpcional(OpcionalCriarDTO dto) {
        Opcional opcional = new Opcional(dto.nome());
        Opcional salvo = opcionais.save(opcional);
        log.info("Opcional criado: {}", salvo.getNome());
        return new OpcionalRespostaDTO(salvo.getId(), salvo.getNome());
    }

    private EspecificacaoVeiculoRespostaDTO toEspecificacaoResposta(EspecificacaoVeiculo esp) {
        return new EspecificacaoVeiculoRespostaDTO(
                esp.getId(),
                esp.getAno(),
                esp.getCombustivel(),
                esp.getCambio(),
                esp.getCarroceria(),
                esp.getPortas()
        );
    }
}

