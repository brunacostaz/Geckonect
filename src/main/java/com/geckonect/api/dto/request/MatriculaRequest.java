package com.geckonect.api.dto.request;

import com.geckonect.domain.enums.StatusMatricula;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatriculaRequest {

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "O ID da trilha é obrigatório.")
    private Long trilhaId;

    @NotNull(message = "O status da matrícula é obrigatório.")
    private StatusMatricula status;
}