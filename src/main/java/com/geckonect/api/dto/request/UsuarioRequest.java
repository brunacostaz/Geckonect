package com.geckonect.api.dto.request;

import com.geckonect.domain.enums.NivelCarreira;
import com.geckonect.domain.enums.PilarPrincipal;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioRequest {

    @NotBlank(message = "O nome do usuário é obrigatório.")
    @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Formato de email inválido.")
    @Size(max = 150, message = "O email não pode exceder 150 caracteres.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha; // Adicionando campo Senha.

    @Size(max = 100, message = "A área de atuação não pode exceder 100 caracteres.")
    private String areaAtuacao;

    private NivelCarreira nivelCarreira;

    private PilarPrincipal pilarPrincipal;
}