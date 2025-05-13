package com.teste.testeFCamara.interfaces.controller;

import com.teste.testeFCamara.application.service.UsuarioService;
import com.teste.testeFCamara.domain.dto.UsuarioDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> criar(@RequestBody @Valid UsuarioDto dto) {
        UsuarioDto usuarioCriado = usuarioService.criarComEnderecos(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }


    @GetMapping
    public ResponseEntity<Page<UsuarioDto>> listarUsuarios(@RequestParam(defaultValue = "0") int pagina, @RequestParam(defaultValue = "0") int ordenacao) {
        Page<UsuarioDto> usuarios = usuarioService.listarPaginado(pagina, ordenacao);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping("/usuario")
    public ResponseEntity<UsuarioDto> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, usuarioDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}