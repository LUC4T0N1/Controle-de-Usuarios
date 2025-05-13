package com.teste.testeFCamara.application.service;

import com.teste.testeFCamara.domain.dto.CadastroDto;
import com.teste.testeFCamara.domain.dto.LoginDto;
import com.teste.testeFCamara.domain.dto.RespostaLoginDto;
import com.teste.testeFCamara.domain.entity.Usuario;
import com.teste.testeFCamara.domain.exception.UnauthorizedException;
import com.teste.testeFCamara.infrastructure.repository.UsuarioRepository;
import com.teste.testeFCamara.infrastructure.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RespostaLoginDto login(LoginDto loginDto) {
        var usuarioOpt = usuarioRepository.buscarPorEmail(loginDto.getEmail());

        if (usuarioOpt.isEmpty() || !passwordEncoder.matches(loginDto.getSenha(), usuarioOpt.get().getSenha())) {
            throw new UnauthorizedException("Credenciais inválidas");
        }

        String token = jwtTokenProvider.generateToken(usuarioOpt.get());
        return new RespostaLoginDto(token);
    }

    public void cadastrar(CadastroDto cadastroDto) {
        var usuarioExistente = usuarioRepository.buscarPorEmail(cadastroDto.getEmail());

        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroDto.getNome());
        usuario.setEmail(cadastroDto.getEmail());
        usuario.setSenha(passwordEncoder.encode(cadastroDto.getSenha()));
        usuario.setRole(Usuario.Role.USUARIO_COMUM);

        usuarioRepository.salvar(usuario);
    }
}
