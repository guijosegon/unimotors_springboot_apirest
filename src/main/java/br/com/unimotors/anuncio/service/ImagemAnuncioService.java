package br.com.unimotors.anuncio.service;

import br.com.unimotors.anuncio.dto.ImagemAnuncioCadastroDTO;
import br.com.unimotors.anuncio.dto.ImagemAnuncioRespostaDTO;
import br.com.unimotors.anuncio.model.AnuncioVeiculo;
import br.com.unimotors.anuncio.model.ImagemAnuncio;
import br.com.unimotors.anuncio.repository.AnuncioRepository;
import br.com.unimotors.anuncio.repository.ImagemAnuncioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImagemAnuncioService {

    private final ImagemAnuncioRepository imagemRepo;
    private final AnuncioRepository anuncioRepo;

    public ImagemAnuncioService(
            ImagemAnuncioRepository imagemRepo,
            AnuncioRepository anuncioRepo
    ) {
        this.imagemRepo = imagemRepo;
        this.anuncioRepo = anuncioRepo;
    }

    public ImagemAnuncioRespostaDTO adicionar(ImagemAnuncioCadastroDTO dto) {

        AnuncioVeiculo anuncio = anuncioRepo.findById(dto.anuncioId())
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado."));

        ImagemAnuncio imagem = new ImagemAnuncio(
                anuncio,
                dto.url(),
                dto.ordem()
        );

        imagemRepo.save(imagem);

        return new ImagemAnuncioRespostaDTO(
                imagem.getId(),
                imagem.getUrl(),
                imagem.getOrdem(),
                anuncio.getId()
        );
    }

    public List<ImagemAnuncioRespostaDTO> listarPorAnuncio(UUID anuncioId) {
        return imagemRepo.findByAnuncioIdOrderByOrdemAsc(anuncioId)
                .stream()
                .map(img -> new ImagemAnuncioRespostaDTO(
                        img.getId(),
                        img.getUrl(),
                        img.getOrdem(),
                        img.getAnuncio().getId()
                ))
                .toList();
    }

    public void remover(UUID id) {
        if (!imagemRepo.existsById(id)) {
            throw new RuntimeException("Imagem não encontrada.");
        }
        imagemRepo.deleteById(id);
    }
}
