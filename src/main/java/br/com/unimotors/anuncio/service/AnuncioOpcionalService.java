package br.com.unimotors.anuncio.service;

import br.com.unimotors.anuncio.dto.AnuncioOpcionalCadastroDTO;
import br.com.unimotors.anuncio.dto.AnuncioOpcionalRespostaDTO;
import br.com.unimotors.anuncio.model.AnuncioOpcional;
import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import br.com.unimotors.anuncio.repository.AnuncioOpcionalRepository;
import br.com.unimotors.anuncio.repository.AnuncioRepository;
import br.com.unimotors.catalogo.model.Opcional;
import br.com.unimotors.catalogo.repository.OpcionalRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnuncioOpcionalService {

    private final AnuncioOpcionalRepository repo;
    private final AnuncioRepository anuncioRepo;
    private final OpcionalRepository opcionalRepo;

    public AnuncioOpcionalService(
            AnuncioOpcionalRepository repo,
            AnuncioRepository anuncioRepo,
            OpcionalRepository opcionalRepo
    ) {
        this.repo = repo;
        this.anuncioRepo = anuncioRepo;
        this.opcionalRepo = opcionalRepo;
    }

    public AnuncioOpcionalRespostaDTO adicionar(AnuncioOpcionalCadastroDTO dto) {

        if (repo.existsByAnuncioIdAndOpcionalId(dto.anuncioId(), dto.opcionalId())) {
            throw new RuntimeException("Esse opcional já está vinculado ao anúncio.");
        }

        AnuncioVeiculo anuncio = anuncioRepo.findById(dto.anuncioId())
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado."));

        Opcional opcional = opcionalRepo.findById(dto.opcionalId())
                .orElseThrow(() -> new RuntimeException("Opcional não encontrado."));

        AnuncioOpcional ao = new AnuncioOpcional(anuncio, opcional);
        repo.save(ao);

        return new AnuncioOpcionalRespostaDTO(
                ao.getId(),
                anuncio.getId(),
                opcional.getId(),
                opcional.getNome()
        );
    }

    public List<AnuncioOpcionalRespostaDTO> listarPorAnuncio(UUID anuncioId) {
        return repo.findByAnuncioId(anuncioId)
                .stream()
                .map(ao -> new AnuncioOpcionalRespostaDTO(
                        ao.getId(),
                        ao.getAnuncio().getId(),
                        ao.getOpcional().getId(),
                        ao.getOpcional().getNome()
                ))
                .toList();
    }

    public void remover(UUID id) {
        repo.deleteById(id);
    }
}
