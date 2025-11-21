package com.geckonect.api.dto.response;

import com.geckonect.domain.enums.StatusMatricula;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class MatriculaResponse {

    private Long id;
    private Long usuarioId;
    private String nomeUsuario;
    private Long trilhaId;
    private String nomeTrilha;
    private LocalDate dataInscricao;
    private StatusMatricula status;
}