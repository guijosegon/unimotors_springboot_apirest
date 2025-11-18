package br.com.unimotors.catalogo.service;

import br.com.unimotors.catalogo.dto.ModeloCadastroDTO;
import br.com.unimotors.catalogo.dto.ModeloRespostaDTO;
import br.com.unimotors.catalogo.model.Marca;
import br.com.unimotors.catalogo.model.Modelo;
import br.com.unimotors.catalogo.repository.MarcaRepository;
import br.com.unimotors.catalogo.repository.ModeloRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModeloService {

    private final ModeloRepository repo;
    private final MarcaRepository marcaRepo;

    public ModeloService(ModeloRepository repo, MarcaRepository marcaRepo) {
        this.repo = repo;
        this.marcaRepo = marcaRepo;
    }

    public ModeloRespostaDTO criar(ModeloCadastroDTO dto) {

        if (repo.existsByNomeIgnoreCaseAndAnoAndMarca_Id(dto.nome(), dto.ano(), dto.marcaId())) {
            throw new RuntimeException("Modelo já cadastrado para essa marca e ano.");
        }

        Marca marca = marcaRepo.findById(dto.marcaId())
                .orElseThrow(() -> new RuntimeException("Marca não encontrada."));

        Modelo modelo = new Modelo(
                marca,
                dto.nome(),
                dto.ano(),
                dto.categoria()
        );

        repo.save(modelo);

        return new ModeloRespostaDTO(
                modelo.getId(),
                modelo.getNome(),
                modelo.getAno(),
                modelo.getCategoria(),
                marca.getId(),
                marca.getNome()
        );
    }

    public ModeloRespostaDTO atualizar(UUID id, ModeloCadastroDTO dto) {

        Modelo modelo = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado."));

        Marca marca = marcaRepo.findById(dto.marcaId())
                .orElseThrow(() -> new RuntimeException("Marca não encontrada."));

        modelo.setNome(dto.nome());
        modelo.setAno(dto.ano());
        modelo.setCategoria(dto.categoria());
        modelo.setMarca(marca);

        repo.save(modelo);

        return new ModeloRespostaDTO(
                modelo.getId(),
                modelo.getNome(),
                modelo.getAno(),
                modelo.getCategoria(),
                marca.getId(),
                marca.getNome()
        );
    }

    public List<ModeloRespostaDTO> listar() {
        return repo.findAll()
                .stream()
                .map(m -> new ModeloRespostaDTO(
                        m.getId(),
                        m.getNome(),
                        m.getAno(),
                        m.getCategoria(),
                        m.getMarca().getId(),
                        m.getMarca().getNome()
                ))
                .toList();
    }

    public ModeloRespostaDTO buscarPorId(UUID id) {

        Modelo m = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado."));

        return new ModeloRespostaDTO(
                m.getId(),
                m.getNome(),
                m.getAno(),
                m.getCategoria(),
                m.getMarca().getId(),
                m.getMarca().getNome()
        );
    }

    public void deletar(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Modelo não encontrado.");
        }

        repo.deleteById(id);
    }
}
