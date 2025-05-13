package com.teste.testeFCamara;

import com.teste.testeFCamara.application.service.AutenticacaoService;

import com.teste.testeFCamara.domain.dto.LoginDto;
import com.teste.testeFCamara.domain.dto.RespostaLoginDto;
import com.teste.testeFCamara.domain.entity.Usuario;
import com.teste.testeFCamara.domain.exception.UnauthorizedException;
import com.teste.testeFCamara.infrastructure.repository.UsuarioRepository;
import com.teste.testeFCamara.infrastructure.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@Transactional
@Rollback
@ExtendWith(MockitoExtension.class)
public class AutenticacaoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Test
    public void deveLogarComCredenciaisValidas() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha(new BCryptPasswordEncoder().encode("senha123"));

        Mockito.when(usuarioRepository.buscarPorEmail("teste@email.com"))
                .thenReturn(Optional.of(usuario));
        Mockito.when(jwtTokenProvider.generateToken(usuario)).thenReturn("fake-token");

        LoginDto dto = new LoginDto();
        dto.setEmail("teste@email.com");
        dto.setSenha("senha123");

        RespostaLoginDto resposta = autenticacaoService.login(dto);
        Assertions.assertEquals("fake-token", resposta.getToken());
    }

    @Test
    public void deveLancarExcecaoParaCredenciaisInvalidas() {
        Mockito.when(usuarioRepository.buscarPorEmail("email@invalido.com"))
                .thenReturn(Optional.empty());

        LoginDto dto = new LoginDto();
        dto.setEmail("email@invalido.com");
        dto.setSenha("qualquer");

        Assertions.assertThrows(UnauthorizedException.class, () -> autenticacaoService.login(dto));
    }
}
