package com.geckonect.service;

import com.geckonect.api.dto.request.TrilhaRequest;
import com.geckonect.api.dto.response.CompetenciaResponse;
import com.geckonect.api.dto.response.TrilhaResponse;
import com.geckonect.domain.exception.CompetenciaNaoEncontradaException;
import com.geckonect.domain.exception.TrilhaNaoEncontradaException;
import com.geckonect.domain.model.Competencia;
import com.geckonect.domain.model.Trilha;
import com.geckonect.domain.repository.CompetenciaRepository;
import com.geckonect.domain.repository.TrilhaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrilhaService {

    private final TrilhaRepository trilhaRepository;
    private final CompetenciaRepository competenciaRepository;

    public TrilhaService(TrilhaRepository trilhaRepository, CompetenciaRepository competenciaRepository) {
        this.trilhaRepository = trilhaRepository;
        this.competenciaRepository = competenciaRepository;
    }

    /**
     * Mapeia o DTO de requisição para a entidade Trilha
     */
    private Trilha toEntity(TrilhaRequest request) {
        return Trilha.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .tipoTrilha(request.getTipoTrilha())
                .nivel(request.getNivel())
                .cargaHoraria(request.getCargaHoraria())
                .focoPrincipal(request.getFocoPrincipal())
                .ativo(request.getAtivo())
                .build();
    }

    /**
     * Mapeia a entidade Trilha para o DTO de resposta
     */
    private TrilhaResponse toResponse(Trilha trilha) {
        // Converte a lista de Competencias (Entity) para o DTO de Response
        Set<CompetenciaResponse> competenciasResponse = trilha.getCompetencias() != null ?
                trilha.getCompetencias().stream()
                        .map(this::toCompetenciaResponse)
                        .collect(Collectors.toSet())
                : Collections.emptySet();

        return TrilhaResponse.builder()
                .id(trilha.getId())
                .nome(trilha.getNome())
                .descricao(trilha.getDescricao())
                .tipoTrilha(trilha.getTipoTrilha())
                .nivel(trilha.getNivel())
                .cargaHoraria(trilha.getCargaHoraria())
                .focoPrincipal(trilha.getFocoPrincipal())
                .ativo(trilha.getAtivo())
                .competencias(competenciasResponse)
                .build();
    }

    /**
     * Mapeia a entidade Competencia para o DTO de resposta
     */
    private CompetenciaResponse toCompetenciaResponse(Competencia competencia) {
        return CompetenciaResponse.builder()
                .id(competencia.getId())
                .nome(competencia.getNome())
                .categoria(competencia.getCategoria())
                .descricao(competencia.getDescricao())
                .build();
    }

    /**
     * Busca e verifica a existência das Competências pelos IDs
     */
    private Set<Competencia> findCompetenciasByIds(Set<Long> competenciaIds) {
        if (competenciaIds == null || competenciaIds.isEmpty()) {
            return Collections.emptySet();
        }

        // Busca todas as competências que correspondem aos IDs
        List<Competencia> competenciasList = competenciaRepository.findAllById(competenciaIds);

        if (competenciasList.size() != competenciaIds.size()) {
            throw new CompetenciaNaoEncontradaException("Uma ou mais Competências não foram encontradas.");
        }
        return Set.copyOf(competenciasList);
    }

    // Métodos CRUD

    /**
     * Cria uma nova trilha e associa as competências
     */
    @Transactional
    public TrilhaResponse criarTrilha(TrilhaRequest request) {
        Trilha novaTrilha = toEntity(request);

        Set<Competencia> competencias = findCompetenciasByIds(request.getCompetenciasIds());
        novaTrilha.setCompetencias(competencias);

        Trilha trilhaSalva = trilhaRepository.save(novaTrilha);
        return toResponse(trilhaSalva);
    }

    /**
     * Lista todas as trilhas
     */
    @Transactional(readOnly = true)
    public List<TrilhaResponse> listarTodas() {
        return trilhaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma trilha pelo ID
     */
    @Transactional(readOnly = true)
    public TrilhaResponse buscarPorId(Long id) {
        Trilha trilha = trilhaRepository.findById(id)
                .orElseThrow(() -> new TrilhaNaoEncontradaException("Trilha não encontrada com o ID: " + id));
        return toResponse(trilha);
    }

    /**
     * Atualiza uma trilha existente
     */
    @Transactional
    public TrilhaResponse atualizarTrilha(Long id, TrilhaRequest request) {
        Trilha trilhaExistente = trilhaRepository.findById(id)
                .orElseThrow(() -> new TrilhaNaoEncontradaException("Trilha não encontrada com o ID: " + id));

        trilhaExistente.setNome(request.getNome());
        trilhaExistente.setDescricao(request.getDescricao());
        trilhaExistente.setTipoTrilha(request.getTipoTrilha());
        trilhaExistente.setNivel(request.getNivel());
        trilhaExistente.setCargaHoraria(request.getCargaHoraria());
        trilhaExistente.setFocoPrincipal(request.getFocoPrincipal());
        trilhaExistente.setAtivo(request.getAtivo());

        // Atualiza relacionamento N:N
        Set<Competencia> competencias = findCompetenciasByIds(request.getCompetenciasIds());
        trilhaExistente.setCompetencias(competencias);

        Trilha trilhaAtualizada = trilhaRepository.save(trilhaExistente);
        return toResponse(trilhaAtualizada);
    }

    /**
     * Deleta uma trilha pelo ID
     */
    @Transactional
    public void deletarTrilha(Long id) {
        if (!trilhaRepository.existsById(id)) {
            throw new TrilhaNaoEncontradaException("Trilha não encontrada com o ID: " + id);
        }
        trilhaRepository.deleteById(id);
    }
}