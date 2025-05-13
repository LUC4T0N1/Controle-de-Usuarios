package com.teste.testeFCamara.interfaces.controller;

import com.teste.testeFCamara.application.service.AutenticacaoService;
import com.teste.testeFCamara.domain.dto.CadastroDto;
import com.teste.testeFCamara.domain.dto.LoginDto;
import com.teste.testeFCamara.domain.dto.RespostaLoginDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public ResponseEntity<RespostaLoginDto> login(@RequestBody @Valid LoginDto loginDto) {
        RespostaLoginDto dto = autenticacaoService.login(loginDto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/cadastro")
    public void cadastrar(@RequestBody @Valid CadastroDto cadastroDto) {
        autenticacaoService.cadastrar(cadastroDto);
    }
}
