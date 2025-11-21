package com.geckonect.api.dto.response;

import com.geckonect.domain.enums.NivelCarreira;
import com.geckonect.domain.enums.PilarPrincipal;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String areaAtuacao;
    private NivelCarreira nivelCarreira;
    private PilarPrincipal pilarPrincipal;
    private LocalDate dataCadastro;
}