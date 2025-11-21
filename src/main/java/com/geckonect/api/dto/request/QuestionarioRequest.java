package com.geckonect.api.dto.request;

import com.geckonect.domain.enums.InteresseMigracaoTipo;
import com.geckonect.domain.enums.ModoEstudoPreferido;
import com.geckonect.domain.enums.NivelSaudeMental;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionarioRequest {

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    // CARREIRA ATUAL / HISTÓRICO
    @NotBlank(message = "A carreira atual é obrigatória.")
    @Size(max = 150, message = "A carreira atual não pode exceder 150 caracteres.")
    private String carreiraAtual;

    @NotNull(message = "O tempo na carreira atual é obrigatório.")
    @Min(value = 0, message = "O tempo na carreira deve ser maior ou igual a zero.")
    private Integer tempoCarreiraAtualAnos;

    @NotNull(message = "A informação sobre trabalho em outra carreira é obrigatória.")
    private Boolean jaTrabalhouOutraCarreira;

    private String carreirasAnteriores;

    // INTERESSES E PREFERÊNCIAS
    @NotBlank(message = "A descrição de 'gosta de fazer' é obrigatória.")
    private String gostaDeFazer;

    @NotBlank(message = "A descrição de 'gosta de estudar' é obrigatória.")
    private String gostaDeEstudar;

    @NotNull(message = "O modo de estudo preferido é obrigatório.")
    private ModoEstudoPreferido modoEstudoPreferido;

    @NotNull(message = "O tipo de interesse de migração é obrigatório.")
    private InteresseMigracaoTipo interesseMigracaoTipo;

    @NotBlank(message = "As áreas de interesse futuro são obrigatórias.")
    private String areasInteresseFuturo;

    // DISPONIBILIDADE E OBJETIVOS
    @NotNull(message = "A disponibilidade de horas por semana é obrigatória.")
    @Min(value = 1, message = "A disponibilidade deve ser de pelo menos 1 hora por semana.")
    private Integer disponibilidadeHorasSemana;

    @NotBlank(message = "O objetivo de carreira é obrigatório.")
    private String objetivoCarreira;

    // SAÚDE MENTAL (PERCEPÇÃO - Escala 1-5)
    @NotNull(message = "O nível de estresse é obrigatório.")
    @Min(value = 1, message = "O nível de estresse deve estar entre 1 e 5.")
    @Max(value = 5, message = "O nível de estresse deve estar entre 1 e 5.")
    private Integer estresse;

    @NotNull(message = "O nível de energia é obrigatório.")
    @Min(value = 1, message = "O nível de energia deve estar entre 1 e 5.")
    @Max(value = 5, message = "O nível de energia deve estar entre 1 e 5.")
    private Integer energia;

    @NotNull(message = "O nível de equilíbrio vida-trabalho é obrigatório.")
    @Min(value = 1, message = "O nível de equilíbrio vida-trabalho deve estar entre 1 e 5.")
    @Max(value = 5, message = "O nível de equilíbrio vida-trabalho deve estar entre 1 e 5.")
    private Integer equilibrioVidaTrabalho;

    @NotNull(message = "O nível de autocobrança é obrigatório.")
    @Min(value = 1, message = "O nível de autocobrança deve estar entre 1 e 5.")
    @Max(value = 5, message = "O nível de autocobrança deve estar entre 1 e 5.")
    private Integer autoCobranca;

}