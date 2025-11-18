package br.com.unimotors.catalogo.service;

import br.com.unimotors.catalogo.dto.EspecificacaoVeiculoCadastroDTO;
import br.com.unimotors.catalogo.dto.EspecificacaoVeiculoRespostaDTO;
import br.com.unimotors.catalogo.model.EspecificacaoVeiculo;
import br.com.unimotors.catalogo.model.Modelo;
import br.com.unimotors.catalogo.repository.EspecificacaoVeiculoRepository;
import br.com.unimotors.catalogo.repository.ModeloRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EspecificacaoVeiculoService {

    private final EspecificacaoVeiculoRepository repo;
    private final ModeloRepository modeloRepo;

    public EspecificacaoVeiculoService(
            EspecificacaoVeiculoRepository repo,
            ModeloRepository modeloRepo
    ) {
        this.repo = repo;
        this.modeloRepo = modeloRepo;
    }

    public EspecificacaoVeiculoRespostaDTO criar(EspecificacaoVeiculoCadastroDTO dto) {

        Modelo modelo = modeloRepo.findById(dto.modeloId())
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado."));

        EspecificacaoVeiculo espec = new EspecificacaoVeiculo(
                modelo,
                dto.ano(),
                dto.combustivel(),
                dto.cambio(),
                dto.carroceria(),
                dto.portas()
        );

        repo.save(espec);

        return converter(espec);
    }

    public EspecificacaoVeiculoRespostaDTO atualizar(UUID id, EspecificacaoVeiculoCadastroDTO dto) {

        EspecificacaoVeiculo espec = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Especificação não encontrada."));

        Modelo modelo = modeloRepo.findById(dto.modeloId())
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado."));

        espec.setModelo(modelo);
        espec.setAno(dto.ano());
        espec.setCombustivel(dto.combustivel());
        espec.setCambio(dto.cambio());
        espec.setCarroceria(dto.carroceria());
        espec.setPortas(dto.portas());

        repo.save(espec);

        return converter(espec);
    }

    public List<EspecificacaoVeiculoRespostaDTO> listar() {
        return repo.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    public EspecificacaoVeiculoRespostaDTO buscarPorId(UUID id) {
        EspecificacaoVeiculo espec = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Especificação não encontrada."));
        return converter(espec);
    }

    public void deletar(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Especificação não encontrada.");
        }
        repo.deleteById(id);
    }

    private EspecificacaoVeiculoRespostaDTO converter(EspecificacaoVeiculo e) {
        return new EspecificacaoVeiculoRespostaDTO(
                e.getId(),
                e.getModelo().getId(),
                e.getModelo().getNome(),
                e.getAno(),
                e.getCombustivel(),
                e.getCambio(),
                e.getCarroceria(),
                e.getPortas()
        );
    }
}
