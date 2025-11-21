package com.geckonect.api.controller;

import com.geckonect.api.dto.request.CompetenciaRequest;
import com.geckonect.api.dto.response.CompetenciaResponse;
import com.geckonect.domain.service.CompetenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competencias")
public class CompetenciaController {

    private final CompetenciaService competenciaService;

    public CompetenciaController(CompetenciaService competenciaService) {
        this.competenciaService = competenciaService;
    }

    /**
     * Cria uma nova competência
     */
    @PostMapping
    public ResponseEntity<CompetenciaResponse> criar(@Valid @RequestBody CompetenciaRequest competenciaRequest) {
        CompetenciaResponse novaCompetencia = competenciaService.criarCompetencia(competenciaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCompetencia);
    }

    /**
     * Lista todas as competências
     */
    @GetMapping
    public ResponseEntity<List<CompetenciaResponse>> listar() {
        List<CompetenciaResponse> competencias = competenciaService.listarTodas();
        return ResponseEntity.ok(competencias);
    }

    /**
     * Busca uma competência pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompetenciaResponse> buscarPorId(@PathVariable Long id) {
        CompetenciaResponse competencia = competenciaService.buscarPorId(id);
        return ResponseEntity.ok(competencia);
    }

    /**
     * Atualiza uma competência existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompetenciaResponse> atualizar(@PathVariable Long id,
                                                         @Valid @RequestBody CompetenciaRequest competenciaRequest) {
        CompetenciaResponse competenciaAtualizada = competenciaService.atualizarCompetencia(id, competenciaRequest);
        return ResponseEntity.ok(competenciaAtualizada);
    }

    /**
     * Deleta uma competência
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        competenciaService.deletarCompetencia(id);
        return ResponseEntity.noContent().build();
    }
}