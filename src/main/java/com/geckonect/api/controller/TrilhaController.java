package com.geckonect.api.controller;

import com.geckonect.api.dto.request.TrilhaRequest;
import com.geckonect.api.dto.response.TrilhaResponse;
import com.geckonect.service.TrilhaService; // CORREÇÃO: Importa o Service do pacote correto
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trilhas")
public class TrilhaController {

    private final TrilhaService trilhaService;

    public TrilhaController(TrilhaService trilhaService) {
        this.trilhaService = trilhaService;
    }

    /**
     * Cria uma nova trilha
     */
    @PostMapping
    public ResponseEntity<TrilhaResponse> criar(@Valid @RequestBody TrilhaRequest trilhaRequest) {
        TrilhaResponse novaTrilha = trilhaService.criarTrilha(trilhaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTrilha);
    }

    /**
     * Lista todas as trilhas
     */
    @GetMapping
    public ResponseEntity<List<TrilhaResponse>> listar() {
        List<TrilhaResponse> trilhas = trilhaService.listarTodas();
        return ResponseEntity.ok(trilhas);
    }

    /**
     * Busca uma trilha pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrilhaResponse> buscarPorId(@PathVariable Long id) {
        TrilhaResponse trilha = trilhaService.buscarPorId(id);
        return ResponseEntity.ok(trilha);
    }

    /**
     * Atualiza uma trilha existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrilhaResponse> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody TrilhaRequest trilhaRequest) {
        TrilhaResponse trilhaAtualizada = trilhaService.atualizarTrilha(id, trilhaRequest);
        return ResponseEntity.ok(trilhaAtualizada);
    }

    /**
     * Deleta uma trilha
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        trilhaService.deletarTrilha(id);
        return ResponseEntity.noContent().build();
    }
}