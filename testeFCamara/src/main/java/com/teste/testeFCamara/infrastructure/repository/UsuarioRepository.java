package com.teste.testeFCamara.infrastructure.repository;

import com.teste.testeFCamara.domain.dto.UsuarioDto;
import com.teste.testeFCamara.domain.entity.Usuario;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UsuarioRepository {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    Page<UsuarioDto> listarPaginado(int pagina, int ordenacao);
    void deletar(Long id);
}