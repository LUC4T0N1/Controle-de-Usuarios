package com.teste.testeFCamara.domain.dto;


public class RespostaLoginDto {

    private String token;

    public RespostaLoginDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
