package com.geckonect.domain.model;

import com.geckonect.domain.enums.CategoriaCompetencia;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "competencias")
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private CategoriaCompetencia categoria;

    @Column(columnDefinition = "TEXT")
    private String descricao;
}