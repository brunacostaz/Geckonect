package com.geckonect.domain.repository;

import com.geckonect.domain.model.Questionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionarioRepository extends JpaRepository<Questionario, Long> {
    Optional<Questionario> findFirstByUsuarioIdOrderByDataRespostaDesc(Long usuarioId);
}
