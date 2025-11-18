package br.com.unimotors.catalogo.service;

import br.com.unimotors.catalogo.dto.MarcaCadastroDTO;
import br.com.unimotors.catalogo.dto.MarcaRespostaDTO;
import br.com.unimotors.catalogo.model.Marca;
import br.com.unimotors.catalogo.repository.MarcaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MarcaService {

    private final MarcaRepository repo;

    public MarcaService(MarcaRepository repo) {
        this.repo = repo;
    }

    public MarcaRespostaDTO criar(MarcaCadastroDTO dto) {

        if (repo.existsByNomeIgnoreCase(dto.nome())) {
            throw new RuntimeException("Já existe uma marca com esse nome.");
        }

        Marca marca = new Marca(dto.nome());
        repo.save(marca);

        return new MarcaRespostaDTO(marca.getId(), marca.getNome());
    }

    public MarcaRespostaDTO atualizar(UUID id, MarcaCadastroDTO dto) {

        Marca marca = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada."));

        marca.setNome(dto.nome());
        repo.save(marca);

        return new MarcaRespostaDTO(marca.getId(), marca.getNome());
    }

    public List<MarcaRespostaDTO> listar() {
        return repo.findAll()
                .stream()
                .map(m -> new MarcaRespostaDTO(m.getId(), m.getNome()))
                .toList();
    }

    public MarcaRespostaDTO buscarPorId(UUID id) {
        Marca marca = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada."));
        return new MarcaRespostaDTO(marca.getId(), marca.getNome());
    }

    public void deletar(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Marca não encontrada.");
        }
        repo.deleteById(id);
    }
}
