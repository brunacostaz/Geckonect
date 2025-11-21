package com.geckonect.api.dto.response;

import com.geckonect.domain.enums.NivelTrilha;
import com.geckonect.domain.enums.TipoTrilha;
import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class TrilhaResponse {

    private Long id;
    private String nome;
    private String descricao;
    private TipoTrilha tipoTrilha;
    private NivelTrilha nivel;
    private Integer cargaHoraria;
    private String focoPrincipal;
    private Boolean ativo;
    private Set<CompetenciaResponse> competencias;
}