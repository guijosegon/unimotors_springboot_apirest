package br.com.unimotors.catalogo.service;

import br.com.unimotors.catalogo.dto.OpcionalCadastroDTO;
import br.com.unimotors.catalogo.dto.OpcionalRespostaDTO;
import br.com.unimotors.catalogo.model.Opcional;
import br.com.unimotors.catalogo.repository.OpcionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OpcionalService {

    private final OpcionalRepository repo;

    public OpcionalService(OpcionalRepository repo) {
        this.repo = repo;
    }

    public OpcionalRespostaDTO criar(OpcionalCadastroDTO dto) {

        if (repo.existsByNomeIgnoreCase(dto.nome())) {
            throw new RuntimeException("Já existe um opcional com esse nome.");
        }

        Opcional opc = new Opcional(dto.nome(), dto.descricao());
        repo.save(opc);

        return new OpcionalRespostaDTO(opc.getId(), opc.getNome(), opc.getDescricao());
    }

    public OpcionalRespostaDTO atualizar(UUID id, OpcionalCadastroDTO dto) {

        Opcional opc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Opcional não encontrado."));

        opc.setNome(dto.nome());
        opc.setDescricao(dto.descricao());

        repo.save(opc);

        return new OpcionalRespostaDTO(opc.getId(), opc.getNome(), opc.getDescricao());
    }

    public void deletar(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Opcional não encontrado.");
        }
        repo.deleteById(id);
    }

    public List<OpcionalRespostaDTO> listar() {
        return repo.findAll()
                .stream()
                .map(o -> new OpcionalRespostaDTO(o.getId(), o.getNome(), o.getDescricao()))
                .toList();
    }

    public OpcionalRespostaDTO buscarPorId(UUID id) {
        Opcional opc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Opcional não encontrado."));

        return new OpcionalRespostaDTO(opc.getId(), opc.getNome(), opc.getDescricao());
    }
}
