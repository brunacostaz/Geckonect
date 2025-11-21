package com.geckonect.service;

import com.geckonect.domain.model.Questionario;
import org.springframework.stereotype.Service;

@Service
public class AnaliseCarreiraAtualService {

    /**
     * Simula a avaliação do risco de automação e necessidade de Reskilling
     * Retorna um pilar de recomendação (TECNICA ou GESTAO)
     */
    public String analisarRisco(Questionario questionario) {
        String carreira = questionario.getCarreiraAtual().toLowerCase();

        // se a pessoa está há muito tempo na carreira (> 10 anos) e a área é de alto risco
        if (questionario.getTempoCarreiraAtualAnos() > 10 && (carreira.contains("administrativo") || carreira.contains("logística"))) {
            return "RESKILLING_ALTO_RISCO";
        }
        // se a pessoa é junior (< 3 anos) ou está em transição, e precisa de base
        if (questionario.getTempoCarreiraAtualAnos() < 3) {
            return "UPSKILLING_INICIAL";
        }
        return "UPSKILLING_MID_LEVEL";
    }
}