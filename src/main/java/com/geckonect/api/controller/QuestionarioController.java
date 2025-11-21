package com.geckonect.api.controller;

import com.geckonect.api.dto.request.QuestionarioRequest;
import com.geckonect.api.dto.response.QuestionarioResponse;
import com.geckonect.service.QuestionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionarios")
public class QuestionarioController {

    private final QuestionarioService questionarioService;

    public QuestionarioController(QuestionarioService questionarioService) {
        this.questionarioService = questionarioService;
    }

    /**
     * Cria um novo questionário inicial e executa a análise da IA
     */
    @PostMapping
    public ResponseEntity<QuestionarioResponse> criar(@Valid @RequestBody QuestionarioRequest request) {
        QuestionarioResponse novoQuestionario = questionarioService.criarQuestionario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoQuestionario);
    }

    /**
     * Lista todos os questionários
     */
    @GetMapping
    public ResponseEntity<List<QuestionarioResponse>> listar() {
        List<QuestionarioResponse> questionarios = questionarioService.listarTodos();
        return ResponseEntity.ok(questionarios);
    }

    /**
     * Busca um questionário pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuestionarioResponse> buscarPorId(@PathVariable Long id) {
        QuestionarioResponse questionario = questionarioService.buscarPorId(id);
        return ResponseEntity.ok(questionario);
    }

    /**
     * Deleta um questionário
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        questionarioService.deletarQuestionario(id);
        return ResponseEntity.noContent().build();
    }
}