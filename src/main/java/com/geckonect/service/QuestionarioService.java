package com.geckonect.service;

import com.geckonect.api.dto.request.QuestionarioRequest;
import com.geckonect.api.dto.response.QuestionarioResponse;
import com.geckonect.domain.enums.NivelSaudeMental;
import com.geckonect.domain.exception.QuestionarioNaoEncontradoException;
import com.geckonect.domain.exception.UsuarioNaoEncontradoException;
import com.geckonect.domain.model.Questionario;
import com.geckonect.domain.model.Usuario;
import com.geckonect.domain.repository.QuestionarioRepository;
import com.geckonect.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionarioService {

    private final QuestionarioRepository questionarioRepository;
    private final UsuarioRepository usuarioRepository;

    public QuestionarioService(QuestionarioRepository questionarioRepository, UsuarioRepository usuarioRepository) {
        this.questionarioRepository = questionarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Mapeia a Entity para o DTO de Response
     */
    private QuestionarioResponse toResponse(Questionario questionario) {
        return QuestionarioResponse.builder()
                .id(questionario.getId())
                .usuarioId(questionario.getUsuario().getId())
                .dataResposta(questionario.getDataResposta())
                .carreiraAtual(questionario.getCarreiraAtual())
                .tempoCarreiraAtualAnos(questionario.getTempoCarreiraAtualAnos())
                .jaTrabalhouOutraCarreira(questionario.getJaTrabalhouOutraCarreira())
                .carreirasAnteriores(questionario.getCarreirasAnteriores())
                .gostaDeFazer(questionario.getGostaDeFazer())
                .gostaDeEstudar(questionario.getGostaDeEstudar())
                .modoEstudoPreferido(questionario.getModoEstudoPreferido())
                .interesseMigracaoTipo(questionario.getInteresseMigracaoTipo())
                .areasInteresseFuturo(questionario.getAreasInteresseFuturo())
                .disponibilidadeHorasSemana(questionario.getDisponibilidadeHorasSemana())
                .objetivoCarreira(questionario.getObjetivoCarreira())
                .estresse(questionario.getEstresse())
                .energia(questionario.getEnergia())
                .equilibrioVidaTrabalho(questionario.getEquilibrioVidaTrabalho())
                .autoCobranca(questionario.getAutoCobranca())
                .indiceSaudeMental(questionario.getIndiceSaudeMental())
                .nivelSaudeMental(questionario.getNivelSaudeMental())
                .riscoAutomacao(questionario.getRiscoAutomacao())
                .observacoesAnalise(questionario.getObservacoesAnalise())
                .build();
    }

    /**
     * Busca e valida a entidade Usuario
     */
    private Usuario findUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    }

    /**
     * SIMULAÇÃO DA LÓGICA DA IA: Calcula o Índice de Saúde Mental e Risco de Automação
     */
    private void realizarAnalise(Questionario questionario) {
        // LÓGICA DE SAÚDE MENTAL (SIMPLIFICADA)
        // Estresse e Autocobrança são pesos negativos. Energia e Equilíbrio são positivos.
        // Valores de 1-5 são mapeados para 0-100. (5 * 4 campos = 20 pontos máximos)
        int scoreSaudeMental = (questionario.getEnergia() + questionario.getEquilibrioVidaTrabalho()) -
                (questionario.getEstresse() + questionario.getAutoCobranca());

        int indice = (int) ( (scoreSaudeMental + 8) / 16.0 * 100 );
        indice = Math.min(100, Math.max(0, indice)); // Garante o limite 0-100
        questionario.setIndiceSaudeMental(indice);

        // Define o Nível de Saúde Mental com base no índice
        if (indice >= 75) {
            questionario.setNivelSaudeMental(NivelSaudeMental.FLORESCENDO);
        } else if (indice >= 50) {
            questionario.setNivelSaudeMental(NivelSaudeMental.ESTAVEL);
        } else if (indice >= 25) {
            questionario.setNivelSaudeMental(NivelSaudeMental.ATENCAO);
        } else {
            questionario.setNivelSaudeMental(NivelSaudeMental.CRITICO);
        }

        // LÓGICA DE RISCO DE AUTOMAÇÃO
        String carreira = questionario.getCarreiraAtual().toLowerCase();
        if (carreira.contains("contábil") || carreira.contains("administrativo") || carreira.contains("atendimento")) {
            questionario.setRiscoAutomacao("ALTO (5-10 anos)");
        } else if (carreira.contains("dados") || carreira.contains("ia") || carreira.contains("desenvolvimento")) {
            questionario.setRiscoAutomacao("BAIXO (20+ anos)");
        } else {
            questionario.setRiscoAutomacao("MÉDIO (10-15 anos)");
        }

        questionario.setObservacoesAnalise("Análise inicial concluída. Sugestões de trilhas personalizadas serão geradas em seguida.");
    }

    //  Métodos CRUD

    /**
     * Cria um novo questionário, realizando a análise da IA
     */
    @Transactional
    public QuestionarioResponse criarQuestionario(QuestionarioRequest request) {
        Usuario usuario = findUsuario(request.getUsuarioId());

        Questionario novoQuestionario = Questionario.builder()
                .usuario(usuario)
                .dataResposta(LocalDate.now())
                .carreiraAtual(request.getCarreiraAtual())
                .tempoCarreiraAtualAnos(request.getTempoCarreiraAtualAnos())
                .jaTrabalhouOutraCarreira(request.getJaTrabalhouOutraCarreira())
                .carreirasAnteriores(request.getCarreirasAnteriores())
                .gostaDeFazer(request.getGostaDeFazer())
                .gostaDeEstudar(request.getGostaDeEstudar())
                .modoEstudoPreferido(request.getModoEstudoPreferido())
                .interesseMigracaoTipo(request.getInteresseMigracaoTipo())
                .areasInteresseFuturo(request.getAreasInteresseFuturo())
                .disponibilidadeHorasSemana(request.getDisponibilidadeHorasSemana())
                .objetivoCarreira(request.getObjetivoCarreira())
                .estresse(request.getEstresse())
                .energia(request.getEnergia())
                .equilibrioVidaTrabalho(request.getEquilibrioVidaTrabalho())
                .autoCobranca(request.getAutoCobranca())
                .build();

        realizarAnalise(novoQuestionario);

        Questionario questionarioSalvo = questionarioRepository.save(novoQuestionario);
        return toResponse(questionarioSalvo);
    }

    /**
     * Lista todos os questionários
     */
    @Transactional(readOnly = true)
    public List<QuestionarioResponse> listarTodos() {
        return questionarioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um questionário pelo ID
     */
    @Transactional(readOnly = true)
    public QuestionarioResponse buscarPorId(Long id) {
        Questionario questionario = questionarioRepository.findById(id)
                .orElseThrow(() -> new QuestionarioNaoEncontradoException("Questionário não encontrado com o ID: " + id));
        return toResponse(questionario);
    }

    /**
     * Deleta um questionário
     */
    @Transactional
    public void deletarQuestionario(Long id) {
        if (!questionarioRepository.existsById(id)) {
            throw new QuestionarioNaoEncontradoException("Questionário não encontrado com o ID: " + id);
        }
        questionarioRepository.deleteById(id);
    }

}