package com.teste.testeFCamara.criarPrimeiroAcesso;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CriarPrimeiroAcessoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cadastrarAdminPrimeiroAcesso() throws Exception {
        String json = """
            {
                "nome": "Admin Primeiro Acesso",
                "email": "admin@teste.com",
                "senha": "12345"
            }
        """;

        mockMvc.perform(post("/api/autenticacao/cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

}
