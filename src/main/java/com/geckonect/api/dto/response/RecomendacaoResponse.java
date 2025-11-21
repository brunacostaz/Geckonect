package com.geckonect.api.dto.response;

import com.geckonect.domain.enums.NivelSaudeMental;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecomendacaoResponse {


    private Long questionarioId;
    private Long usuarioId;
    private String carreiraAtual;
    private NivelSaudeMental nivelSaudeMental;
    private String riscoAutomacao;
    private String observacoesAnalise;

    private String focoPrincipalRecomendado;
    private List<TrilhaResponse> trilhasSugeridas;
}