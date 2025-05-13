package com.teste.testeFCamara.application.service;

import com.teste.testeFCamara.domain.dto.EnderecoDto;
import com.teste.testeFCamara.domain.dto.UsuarioDto;
import com.teste.testeFCamara.domain.entity.Endereco;
import com.teste.testeFCamara.domain.entity.Usuario;
import com.teste.testeFCamara.infrastructure.repository.EnderecoRepository;
import com.teste.testeFCamara.infrastructure.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EnderecoRepository enderecoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, EnderecoRepository enderecoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public UsuarioDto criarComEnderecos(UsuarioDto dto) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setRole(dto.getRole());
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

            List<Endereco> enderecos = dto.getEnderecos().stream().map(e -> {
                Endereco endereco = new Endereco();
                endereco.setLogradouro(e.getLogradouro());
                endereco.setNumero(e.getNumero());
                endereco.setComplemento(e.getComplemento());
                endereco.setBairro(e.getBairro());
                endereco.setCidade(e.getCidade());
                endereco.setEstado(e.getEstado());
                endereco.setCep(e.getCep());
                endereco.setUsuario(usuario);
                return endereco;
            }).collect(Collectors.toList());

            usuario.setEnderecos(enderecos);
            Usuario salvo = usuarioRepository.salvar(usuario);

            return new UsuarioDto(salvo);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Dados inválidos ou em conflito: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage(), e);
        }
    }

    public Page<UsuarioDto> listarPaginado(int pagina, int ordenacao) {
        return usuarioRepository.listarPaginado(pagina, ordenacao);
    }

    public UsuarioDto buscarPorId(Long id) {
        return usuarioRepository.buscarPorId(id)
                .map(UsuarioDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
    }

    public UsuarioDto buscarPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email)
                .map(UsuarioDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + email + " não encontrado."));
    }

    public UsuarioDto atualizar(Long id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));

        try {
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setRole(dto.getRole());

            if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            }

            usuario.getEnderecos().clear();
            if (dto.getEnderecos() != null) {
                for (EnderecoDto enderecoDto : dto.getEnderecos()) {
                    Endereco endereco = new Endereco();
                    endereco.setLogradouro(enderecoDto.getLogradouro());
                    endereco.setNumero(enderecoDto.getNumero());
                    endereco.setComplemento(enderecoDto.getComplemento());
                    endereco.setBairro(enderecoDto.getBairro());
                    endereco.setCidade(enderecoDto.getCidade());
                    endereco.setEstado(enderecoDto.getEstado());
                    endereco.setCep(enderecoDto.getCep());
                    endereco.setUsuario(usuario);
                    enderecoRepository.salvar(endereco);
                    usuario.getEnderecos().add(endereco);
                }
            }
            usuarioRepository.salvar(usuario);
            return new UsuarioDto(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro de integridade ao atualizar usuário: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
        try {
            usuarioRepository.deletar(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }
}
