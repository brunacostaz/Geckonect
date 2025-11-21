package com.geckonect.domain.service;

import com.geckonect.api.dto.request.UsuarioRequest;
import com.geckonect.api.dto.response.UsuarioResponse;
import com.geckonect.domain.exception.UsuarioNaoEncontradoException;
import com.geckonect.domain.model.Usuario;
import com.geckonect.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Conversão do DTO para entidade
     */
    private Usuario toEntity(UsuarioRequest request) {
        return Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                // A senha seria tratada aqui (criptografia, etc.) em um projeto real.
                // Como não estamos persistindo a senha no Model atual, vamos ignorá-la por agora.
                .areaAtuacao(request.getAreaAtuacao())
                .nivelCarreira(request.getNivelCarreira())
                .pilarPrincipal(request.getPilarPrincipal())
                .dataCadastro(LocalDate.now()) // Define a data de cadastro no momento da criação
                .build();
    }

    /**
     * converte a entidade para dto response
     */
    private UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .areaAtuacao(usuario.getAreaAtuacao())
                .nivelCarreira(usuario.getNivelCarreira())
                .pilarPrincipal(usuario.getPilarPrincipal())
                .dataCadastro(usuario.getDataCadastro())
                .build();
    }

    //  Métodos CRUD

    @Transactional
    public UsuarioResponse criarUsuario(UsuarioRequest request) {

        Usuario novoUsuario = toEntity(request);
        novoUsuario = usuarioRepository.save(novoUsuario);
        return toResponse(novoUsuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse atualizarUsuario(Long id, UsuarioRequest request) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));

        usuarioExistente.setNome(request.getNome());
        usuarioExistente.setEmail(request.getEmail());
        usuarioExistente.setAreaAtuacao(request.getAreaAtuacao());
        usuarioExistente.setNivelCarreira(request.getNivelCarreira());
        usuarioExistente.setPilarPrincipal(request.getPilarPrincipal());

        usuarioExistente = usuarioRepository.save(usuarioExistente);
        return toResponse(usuarioExistente);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}