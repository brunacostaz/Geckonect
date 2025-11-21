package com.geckonect.api.dto.response;

import com.geckonect.domain.enums.CategoriaCompetencia;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompetenciaResponse {
    private Long id;
    private String nome;
    private CategoriaCompetencia categoria;
    private String descricao;
}