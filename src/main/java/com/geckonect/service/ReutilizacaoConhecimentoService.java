package com.geckonect.service;

import com.geckonect.domain.model.Questionario;
import org.springframework.stereotype.Service;

@Service
public class ReutilizacaoConhecimentoService {

    /**
     * Simula o mapeamento de gostos e interesses para a área do futuro
     * Retorna a área de conhecimento (IA/DADOS, GESTAO/PESSOAS, ou INOVACAO/DESIGN)
     */
    public String mapearInteresses(Questionario questionario) {
        String gostos = (questionario.getGostaDeFazer() + " " + questionario.getGostaDeEstudar()).toLowerCase();

        if (gostos.contains("análise") || gostos.contains("dados") || gostos.contains("lógica")) {
            return "AREA_IA_DADOS";
        }
        if (gostos.contains("pessoas") || gostos.contains("liderança") || gostos.contains("comunicação")) {
            return "AREA_GESTAO_PESSOAS";
        }
        return "AREA_INOVACAO_DESIGN";
    }
}