package com.geckonect.domain.model;

import com.geckonect.domain.enums.NivelCarreira;
import com.geckonect.domain.enums.PilarPrincipal;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "area_atuacao", length = 100)
    private String areaAtuacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_carreira", length = 50)
    private NivelCarreira nivelCarreira;

    @Enumerated(EnumType.STRING)
    @Column(name = "pilar_principal", length = 50)
    private PilarPrincipal pilarPrincipal;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;
}
