package com.geckonect.domain.model;

import com.geckonect.domain.enums.InteresseMigracaoTipo;
import com.geckonect.domain.enums.ModoEstudoPreferido;
import com.geckonect.domain.enums.NivelSaudeMental;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "questionarios_iniciais")
public class Questionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "data_resposta", nullable = false)
    private LocalDate dataResposta;

    // CARREIRA ATUAL / HISTÓRICO

    @Column(name = "carreira_atual", length = 150)
    private String carreiraAtual;

    @Column(name = "tempo_carreira_atual_anos")
    private Integer tempoCarreiraAtualAnos;

    @Column(name = "ja_trabalhou_outra_carreira")
    private Boolean jaTrabalhouOutraCarreira;

    @Column(name = "carreiras_anteriores", columnDefinition = "TEXT")
    private String carreirasAnteriores;

    // INTERESSES E PREFERÊNCIAS

    @Column(name = "gosta_de_fazer", columnDefinition = "TEXT")
    private String gostaDeFazer;

    @Column(name = "gosta_de_estudar", columnDefinition = "TEXT")
    private String gostaDeEstudar;

    @Enumerated(EnumType.STRING)
    @Column(name = "modo_estudo_preferido", length = 50)
    private ModoEstudoPreferido modoEstudoPreferido;

    @Enumerated(EnumType.STRING)
    @Column(name = "interesse_migracao_tipo", length = 50)
    private InteresseMigracaoTipo interesseMigracaoTipo;

    @Column(name = "areas_interesse_futuro", columnDefinition = "TEXT")
    private String areasInteresseFuturo;

    //  DISPONIBILIDADE E OBJETIVOS

    @Column(name = "disponibilidade_horas_semana")
    private Integer disponibilidadeHorasSemana;

    @Column(name = "objetivo_carreira", columnDefinition = "TEXT")
    private String objetivoCarreira;

    //  SAÚDE MENTAL (PERCEPÇÃO)

    // valores em escala, ex: 1–5
    @Column(name = "saude_estresse")
    private Integer estresse;

    @Column(name = "saude_energia")
    private Integer energia;

    @Column(name = "saude_equilibrio_vida_trabalho")
    private Integer equilibrioVidaTrabalho;

    @Column(name = "saude_auto_cobranca")
    private Integer autoCobranca;

    //  RESULTADOS CALCULADOS

    @Column(name = "indice_saude_mental")
    private Integer indiceSaudeMental; // 0-100

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_saude_mental", length = 50)
    private NivelSaudeMental nivelSaudeMental;

    @Column(name = "risco_automacao", length = 50)
    private String riscoAutomacao;

    @Column(name = "observacoes_analise", columnDefinition = "TEXT")
    private String observacoesAnalise;
}
