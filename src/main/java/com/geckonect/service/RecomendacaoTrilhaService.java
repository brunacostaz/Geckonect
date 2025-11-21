package com.geckonect.service;

import com.geckonect.api.dto.response.RecomendacaoResponse;
import com.geckonect.api.dto.response.TrilhaResponse;
import com.geckonect.domain.exception.QuestionarioNaoEncontradoException;
import com.geckonect.domain.model.Questionario;
import com.geckonect.domain.model.Trilha;
import com.geckonect.domain.repository.QuestionarioRepository;
import com.geckonect.domain.repository.TrilhaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacaoTrilhaService {

    private final AnaliseCarreiraAtualService analiseCarreiraAtualService;
    private final AnaliseSaudeMentalService analiseSaudeMentalService;
    private final ReutilizacaoConhecimentoService reutilizacaoConhecimentoService;
    private final QuestionarioRepository questionarioRepository;
    private final TrilhaRepository trilhaRepository;

    public RecomendacaoTrilhaService(AnaliseCarreiraAtualService analiseCarreiraAtualService,
                                     AnaliseSaudeMentalService analiseSaudeMentalService,
                                     ReutilizacaoConhecimentoService reutilizacaoConhecimentoService,
                                     QuestionarioRepository questionarioRepository,
                                     TrilhaRepository trilhaRepository) {
        this.analiseCarreiraAtualService = analiseCarreiraAtualService;
        this.analiseSaudeMentalService = analiseSaudeMentalService;
        this.reutilizacaoConhecimentoService = reutilizacaoConhecimentoService;
        this.questionarioRepository = questionarioRepository;
        this.trilhaRepository = trilhaRepository;
    }

    /**
     * Mapeia a Entity Trilha para o DTO de Response
     */
    private TrilhaResponse toTrilhaResponse(Trilha trilha) {
        // Mapeamento simplificado, assumindo que as competências não precisam ser totalmente carregadas aqui
        return TrilhaResponse.builder()
                .id(trilha.getId())
                .nome(trilha.getNome())
                .descricao(trilha.getDescricao())
                .cargaHoraria(trilha.getCargaHoraria())
                .nivel(trilha.getNivel())
                .focoPrincipal(trilha.getFocoPrincipal())
                .tipoTrilha(trilha.getTipoTrilha())
                .ativo(trilha.getAtivo())
                .competencias(Collections.emptySet()) // Evita carregar o Set<Competencia> completo aqui
                .build();
    }

    /**
     * Orquestra as análises da IA para gerar uma recomendação de trilha e o relatório
     */
    @Transactional(readOnly = true)
    public RecomendacaoResponse recomendarTrilhas(Long questionarioId) {
        Questionario questionario = questionarioRepository.findById(questionarioId)
                .orElseThrow(() -> new QuestionarioNaoEncontradoException("Questionário não encontrado para análise."));

        //  Coleta Análises
        String focoRisco = analiseCarreiraAtualService.analisarRisco(questionario);
        String focoEmocional = analiseSaudeMentalService.analisarFocoEmocional(questionario);
        String areaFutura = reutilizacaoConhecimentoService.mapearInteresses(questionario);

        // Lógica de Decisão
        String focoBusca;
        String focoPrincipalRecomendado;

        if (focoEmocional.equals("PRIORIDADE_SAUDE_MENTAL")) {
            focoBusca = "saude mental";
            focoPrincipalRecomendado = "Prioridade: Estabilidade Emocional e Bem-Estar";
        } else if (focoRisco.equals("RESKILLING_ALTO_RISCO")) {
            focoBusca = areaFutura.replace("AREA_", "").replace("_", " ");
            focoPrincipalRecomendado = "Reskilling Urgente em " + focoBusca.toUpperCase();
        } else {
            focoBusca = areaFutura.equals("AREA_IA_DADOS") ? "dados" :
                    areaFutura.equals("AREA_GESTAO_PESSOAS") ? "gestão" : "inovação";
            focoPrincipalRecomendado = "Upskilling em " + focoBusca.toUpperCase();
        }

        //  Busca no Banco
        List<Trilha> trilhas = trilhaRepository.findByFocoPrincipalContainingIgnoreCaseAndAtivoTrue(focoBusca);
        List<TrilhaResponse> trilhasResponse = trilhas.stream()
                .map(this::toTrilhaResponse)
                .collect(Collectors.toList());

        // Constrói o Response Final
        return RecomendacaoResponse.builder()
                .questionarioId(questionario.getId())
                .usuarioId(questionario.getUsuario().getId())
                .carreiraAtual(questionario.getCarreiraAtual())
                .nivelSaudeMental(questionario.getNivelSaudeMental())
                .riscoAutomacao(questionario.getRiscoAutomacao())
                .observacoesAnalise(questionario.getObservacoesAnalise())
                .focoPrincipalRecomendado(focoPrincipalRecomendado)
                .trilhasSugeridas(trilhasResponse)
                .build();
    }
}