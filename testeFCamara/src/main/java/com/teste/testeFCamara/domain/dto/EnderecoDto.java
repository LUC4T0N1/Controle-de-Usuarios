package com.teste.testeFCamara.domain.dto;

import com.teste.testeFCamara.domain.entity.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EnderecoDto {
    private Long id;
    @NotBlank(message = "O logradouro é obrigatório.")
    private String logradouro;
    @NotBlank(message = "O número é obrigatório.")
    private String numero;
    private String complemento;
    @NotBlank(message = "O bairro é obrigatório.")
    private String bairro;
    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;
    @NotBlank(message = "O estado é obrigatório.")
    private String estado;
    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "\\d{5}\\d{3}", message = "O CEP deve ter o formato 00000000.")
    private String cep;

    public EnderecoDto(Endereco endereco) {
        this.id = endereco.getId();
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.cep = endereco.getCep();
    }

    public EnderecoDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}