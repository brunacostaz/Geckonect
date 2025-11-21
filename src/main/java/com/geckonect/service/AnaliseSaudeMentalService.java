package com.geckonect.service;

import com.geckonect.domain.enums.NivelSaudeMental;
import com.geckonect.domain.model.Questionario;
import org.springframework.stereotype.Service;

@Service
public class AnaliseSaudeMentalService {

    /**
     * Determina o pilar mais crítico com base no nível de saúde mental
     * retorna o foco principal (SAUDE_MENTAL, SOFTSKILLS_GESTAO, ou HARDSKILLS)
     */
    public String analisarFocoEmocional(Questionario questionario) {
        if (questionario.getNivelSaudeMental() == NivelSaudeMental.CRITICO || questionario.getNivelSaudeMental() == NivelSaudeMental.ATENCAO) {
            return "PRIORIDADE_SAUDE_MENTAL";
        }

        // Se a saúde mental está estável, foca nos gaps de soft skills.
        if (questionario.getEstresse() > 3 || questionario.getAutoCobranca() > 3) {
            return "FOCO_SOFTSKILLS_LIDERANCA";
        }

        return "FOCO_HARDSKILLS_PADRAO";
    }
}