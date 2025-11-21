package com.geckonect.domain.model;

import com.geckonect.domain.enums.NivelTrilha;
import com.geckonect.domain.enums.TipoTrilha;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trilhas")
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoTrilha tipoTrilha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NivelTrilha nivel;

    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @Column(name = "foco_principal", length = 100)
    private String focoPrincipal;

    @Column(nullable = false)
    private Boolean ativo;

    // --- RELACIONAMENTO MUITOS-PARA-MUITOS (N:N) ---
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trilha_competencia",
            joinColumns = @JoinColumn(name = "trilha_id"),
            inverseJoinColumns = @JoinColumn(name = "competencia_id")
    )
    private Set<Competencia> competencias;
}