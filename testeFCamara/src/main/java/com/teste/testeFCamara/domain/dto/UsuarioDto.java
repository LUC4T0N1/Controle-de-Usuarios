package com.teste.testeFCamara.domain.dto;

import com.teste.testeFCamara.domain.entity.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class
UsuarioDto {
    private Long id;
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;
    private String senha;
    @NotBlank(message = "A role é obrigatória.")
    private String role;
    @Valid
    private List<EnderecoDto> enderecos;

    public UsuarioDto(){}

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.role =  usuario.getRole().toString();
        this.enderecos = usuario.getEnderecos().stream()
                .map(EnderecoDto::new)
                .toList();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario.Role getRole() {
        if ("ADMIN".equalsIgnoreCase(role)) {
            return Usuario.Role.ADMIN;
        }
        return Usuario.Role.USUARIO_COMUM;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<EnderecoDto> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDto> enderecos) {
        this.enderecos = enderecos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}