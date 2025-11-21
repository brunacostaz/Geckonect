package com.geckonect.api.controller;

import com.geckonect.api.dto.response.RecomendacaoResponse;
import com.geckonect.service.RecomendacaoTrilhaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recomendacao")
public class RecomendacaoController {

    private final RecomendacaoTrilhaService recomendacaoTrilhaService;

    public RecomendacaoController(RecomendacaoTrilhaService recomendacaoTrilhaService) {
        this.recomendacaoTrilhaService = recomendacaoTrilhaService;
    }

    /**
     * Gera e retorna a recomendação de trilhas baseada nas análises do questionário
     */
    @GetMapping("/{questionarioId}")
    public ResponseEntity<RecomendacaoResponse> recomendar(@PathVariable Long questionarioId) {
        RecomendacaoResponse recomendacao = recomendacaoTrilhaService.recomendarTrilhas(questionarioId);
        return ResponseEntity.ok(recomendacao);
    }
}