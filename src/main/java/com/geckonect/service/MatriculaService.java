package com.geckonect.service;

import com.geckonect.api.dto.request.MatriculaRequest;
import com.geckonect.api.dto.response.MatriculaResponse;
import com.geckonect.domain.exception.MatriculaNaoEncontradaException;
import com.geckonect.domain.exception.TrilhaNaoEncontradaException;
import com.geckonect.domain.exception.UsuarioNaoEncontradoException;
import com.geckonect.domain.model.Matricula;
import com.geckonect.domain.model.Trilha;
import com.geckonect.domain.model.Usuario;
import com.geckonect.domain.repository.MatriculaRepository;
import com.geckonect.domain.repository.TrilhaRepository;
import com.geckonect.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TrilhaRepository trilhaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository, UsuarioRepository usuarioRepository, TrilhaRepository trilhaRepository) {
        this.matriculaRepository = matriculaRepository;
        this.usuarioRepository = usuarioRepository;
        this.trilhaRepository = trilhaRepository;
    }

    /**
     * Mapeia a Entity para o DTO de Response
     */
    private MatriculaResponse toResponse(Matricula matricula) {
        return MatriculaResponse.builder()
                .id(matricula.getId())
                .usuarioId(matricula.getUsuario().getId())
                .nomeUsuario(matricula.getUsuario().getNome())
                .trilhaId(matricula.getTrilha().getId())
                .nomeTrilha(matricula.getTrilha().getNome())
                .dataInscricao(matricula.getDataInscricao())
                .status(matricula.getStatus())
                .build();
    }

    /**
     * Busca e valida as entidades Usuario e Trilha
     */
    private Usuario findUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    }

    private Trilha findTrilha(Long id) {
        return trilhaRepository.findById(id)
                .orElseThrow(() -> new TrilhaNaoEncontradaException("Trilha não encontrada com o ID: " + id));
    }

    //  Métodos CRUD

    /**
     * Realiza uma nova matrícula de um usuário em uma trilha.
     */
    @Transactional
    public MatriculaResponse criarMatricula(MatriculaRequest request) {
        // CORREÇÃO: Usando getUsuarioId() e getTrilhaId()
        Usuario usuario = findUsuario(request.getUsuarioId());
        Trilha trilha = findTrilha(request.getTrilhaId());

        Matricula novaMatricula = Matricula.builder()
                .usuario(usuario)
                .trilha(trilha)
                .dataInscricao(LocalDate.now()) // Define a data de hoje
                .status(request.getStatus())
                .build();

        Matricula matriculaSalva = matriculaRepository.save(novaMatricula);
        return toResponse(matriculaSalva);
    }

    /**
     * Lista todas as matrículas
     */
    @Transactional(readOnly = true)
    public List<MatriculaResponse> listarTodas() {
        return matriculaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma matrícula pelo ID
     */
    @Transactional(readOnly = true)
    public MatriculaResponse buscarPorId(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada com o ID: " + id));
        return toResponse(matricula);
    }

    /**
     * Atualiza o status de uma matrícula existente
     */
    @Transactional
    public MatriculaResponse atualizarMatricula(Long id, MatriculaRequest request) {
        Matricula matriculaExistente = matriculaRepository.findById(id)
                .orElseThrow(() -> new MatriculaNaoEncontradaException("Matrícula não encontrada com o ID: " + id));

        if (!matriculaExistente.getUsuario().getId().equals(request.getUsuarioId())) {
            matriculaExistente.setUsuario(findUsuario(request.getUsuarioId()));
        }
        if (!matriculaExistente.getTrilha().getId().equals(request.getTrilhaId())) {
            matriculaExistente.setTrilha(findTrilha(request.getTrilhaId()));
        }

        // Atualiza apenas o status
        matriculaExistente.setStatus(request.getStatus());

        Matricula matriculaAtualizada = matriculaRepository.save(matriculaExistente);
        return toResponse(matriculaAtualizada);
    }

    /**
     * Deleta uma matrícula
     */
    @Transactional
    public void deletarMatricula(Long id) {
        if (!matriculaRepository.existsById(id)) {
            throw new MatriculaNaoEncontradaException("Matrícula não encontrada com o ID: " + id);
        }
        matriculaRepository.deleteById(id);
    }
}