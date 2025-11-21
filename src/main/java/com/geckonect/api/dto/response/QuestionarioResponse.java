package com.geckonect.api.dto.response;

import com.geckonect.domain.enums.InteresseMigracaoTipo;
import com.geckonect.domain.enums.ModoEstudoPreferido;
import com.geckonect.domain.enums.NivelSaudeMental;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class QuestionarioResponse {

    private Long id;
    private Long usuarioId;
    private LocalDate dataResposta;

    // CARREIRA ATUAL / HISTÓRICO
    private String carreiraAtual;
    private Integer tempoCarreiraAtualAnos;
    private Boolean jaTrabalhouOutraCarreira;
    private String carreirasAnteriores;

    // INTERESSES E PREFERÊNCIAS
    private String gostaDeFazer;
    private String gostaDeEstudar;
    private ModoEstudoPreferido modoEstudoPreferido;
    private InteresseMigracaoTipo interesseMigracaoTipo;
    private String areasInteresseFuturo;

    // DISPONIBILIDADE E OBJETIVOS
    private Integer disponibilidadeHorasSemana;
    private String objetivoCarreira;

    // SAÚDE MENTAL (PERCEPÇÃO)
    private Integer estresse;
    private Integer energia;
    private Integer equilibrioVidaTrabalho;
    private Integer autoCobranca;

    // RESULTADOS CALCULADOS
    private Integer indiceSaudeMental;
    private NivelSaudeMental nivelSaudeMental;
    private String riscoAutomacao;
    private String observacoesAnalise;
}