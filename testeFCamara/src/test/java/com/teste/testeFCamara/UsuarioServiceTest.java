package com.teste.testeFCamara;

import com.teste.testeFCamara.application.service.UsuarioService;
import com.teste.testeFCamara.infrastructure.repository.EnderecoRepository;
import com.teste.testeFCamara.infrastructure.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;
@Transactional
@Rollback
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;


    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        Mockito.when(usuarioRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.buscarPorId(1L);
        });
    }
}
