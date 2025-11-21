package com.geckonect.domain.model;

import com.geckonect.domain.enums.StatusMatricula;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trilha_id")
    private Trilha trilha;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDate dataInscricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusMatricula status;
}
