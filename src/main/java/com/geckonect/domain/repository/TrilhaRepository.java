package com.geckonect.domain.repository;

import com.geckonect.domain.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrilhaRepository extends JpaRepository<Trilha, Long> {
    /**
     * Busca trilhas ativas em que o foco principal tenham palavra-chave
     */
    List<Trilha> findByFocoPrincipalContainingIgnoreCaseAndAtivoTrue(String foco);
}
