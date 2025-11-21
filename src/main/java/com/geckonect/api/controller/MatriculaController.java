package com.geckonect.api.controller;

import com.geckonect.api.dto.request.MatriculaRequest;
import com.geckonect.api.dto.response.MatriculaResponse;
import com.geckonect.service.MatriculaService; // Usa o pacote correto
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    /**
     * Cria uma nova matrícula
     */
    @PostMapping
    public ResponseEntity<MatriculaResponse> criar(@Valid @RequestBody MatriculaRequest request) {
        MatriculaResponse novaMatricula = matriculaService.criarMatricula(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMatricula);
    }

    /**
     * Lista todas as matrículas
     */
    @GetMapping
    public ResponseEntity<List<MatriculaResponse>> listar() {
        List<MatriculaResponse> matriculas = matriculaService.listarTodas();
        return ResponseEntity.ok(matriculas);
    }

    /**
     * Busca uma matrícula pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponse> buscarPorId(@PathVariable Long id) {
        MatriculaResponse matricula = matriculaService.buscarPorId(id);
        return ResponseEntity.ok(matricula);
    }

    /**
     * Atualiza uma matrícula existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<MatriculaResponse> atualizar(@PathVariable Long id,
                                                       @Valid @RequestBody MatriculaRequest request) {
        MatriculaResponse matriculaAtualizada = matriculaService.atualizarMatricula(id, request);
        return ResponseEntity.ok(matriculaAtualizada);
    }

    /**
     * Deleta uma matrícula
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        matriculaService.deletarMatricula(id);
        return ResponseEntity.noContent().build();
    }
}