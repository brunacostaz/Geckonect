package com.geckonect.api.dto.request;

import com.geckonect.domain.enums.NivelTrilha;
import com.geckonect.domain.enums.TipoTrilha;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class TrilhaRequest {

    @NotBlank(message = "O nome da trilha é obrigatório.")
    @Size(max = 150, message = "O nome não pode exceder 150 caracteres.")
    private String nome;

    @NotBlank(message = "A descrição da trilha é obrigatória.")
    private String descricao;

    @NotNull(message = "O tipo da trilha é obrigatório.")
    private TipoTrilha tipoTrilha;

    @NotNull(message = "O nível da trilha é obrigatório.")
    private NivelTrilha nivel;

    @NotNull(message = "A carga horária é obrigatória.")
    @Min(value = 1, message = "A carga horária deve ser positiva.")
    private Integer cargaHoraria;

    @Size(max = 100, message = "O foco principal não pode exceder 100 caracteres.")
    private String focoPrincipal;

    @NotNull(message = "O status ativo é obrigatório.")
    private Boolean ativo;

    private Set<Long> competenciasIds;
}