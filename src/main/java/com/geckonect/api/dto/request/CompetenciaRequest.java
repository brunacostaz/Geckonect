package com.geckonect.api.dto.request;

import com.geckonect.domain.enums.CategoriaCompetencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompetenciaRequest {

    @NotBlank(message = "O nome da competência é obrigatório.")
    @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
    private String nome;

    @NotNull(message = "A categoria da competência é obrigatória.")
    private CategoriaCompetencia categoria;

    @NotBlank(message = "A descrição da competência é obrigatória.")
    private String descricao;
}