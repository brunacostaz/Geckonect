package com.geckonect.domain.service;

import com.geckonect.api.dto.request.CompetenciaRequest;
import com.geckonect.api.dto.response.CompetenciaResponse;
import com.geckonect.domain.exception.CompetenciaNaoEncontradaException;
import com.geckonect.domain.model.Competencia;
import com.geckonect.domain.repository.CompetenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetenciaService {

    private final CompetenciaRepository competenciaRepository;

    public CompetenciaService(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository;
    }

    /**
     * Mapeia DTO de Request para a Entity
     */
    private Competencia toEntity(CompetenciaRequest request) {
        return Competencia.builder()
                .nome(request.getNome())
                .categoria(request.getCategoria())
                .descricao(request.getDescricao())
                .build();
    }

    /**
     * Mapeia Entity para o DTO de Response
     */
    private CompetenciaResponse toResponse(Competencia competencia) {
        return CompetenciaResponse.builder()
                .id(competencia.getId())
                .nome(competencia.getNome())
                .categoria(competencia.getCategoria())
                .descricao(competencia.getDescricao())
                .build();
    }

    //  Métodos CRUD

    /**
     * Cria uma nova competência
     */
    @Transactional
    public CompetenciaResponse criarCompetencia(CompetenciaRequest request) {
        Competencia novaCompetencia = toEntity(request);
        Competencia competenciaSalva = competenciaRepository.save(novaCompetencia);
        return toResponse(competenciaSalva);
    }

    /**
     * Lista todas as competências
     */
    @Transactional(readOnly = true)
    public List<CompetenciaResponse> listarTodas() {
        return competenciaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma competência pelo ID
     */
    @Transactional(readOnly = true)
    public CompetenciaResponse buscarPorId(Long id) {
        Competencia competencia = competenciaRepository.findById(id)
                .orElseThrow(() -> new CompetenciaNaoEncontradaException("Competência não encontrada com o ID: " + id));
        return toResponse(competencia);
    }

    /**
     * Atualiza uma competência existente
     */
    @Transactional
    public CompetenciaResponse atualizarCompetencia(Long id, CompetenciaRequest request) {
        Competencia competenciaExistente = competenciaRepository.findById(id)
                .orElseThrow(() -> new CompetenciaNaoEncontradaException("Competência não encontrada com o ID: " + id));

        competenciaExistente.setNome(request.getNome());
        competenciaExistente.setCategoria(request.getCategoria());
        competenciaExistente.setDescricao(request.getDescricao());

        Competencia competenciaAtualizada = competenciaRepository.save(competenciaExistente);
        return toResponse(competenciaAtualizada);
    }

    /**
     * Deleta uma competência pelo ID
     */
    @Transactional
    public void deletarCompetencia(Long id) {
        if (!competenciaRepository.existsById(id)) {
            throw new CompetenciaNaoEncontradaException("Competência não encontrada com o ID: " + id);
        }
        competenciaRepository.deleteById(id);
    }
}