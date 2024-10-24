package com.davifaustino.rinhabackend.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.davifaustino.rinhabackend.application.ErrorResponse;
import com.davifaustino.rinhabackend.domain.PessoaDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoasTest {
    
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Deve salvar uma pessoa com sucesso")
    void postPessoas1() throws Exception {
        String pessoaDtoString = objectMapper.writeValueAsString(new PessoaDto(null, "Juca", "José", "2000-07-08", Arrays.asList("Java")));
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        UUID.fromString(result.getResponse().getContentAsString().replace("\"", ""));
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 422 por apelido ser nulo")
    void postPessoas2() throws Exception {
        String pessoaDtoString = objectMapper.writeValueAsString(new PessoaDto(null, null, "José", "2000-07-08", Arrays.asList("Java")));
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Valor de campo nulo", errorResponse.message());
        assertEquals(422, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 422 por apelido já existir")
    void postPessoas3() throws Exception {
        String pessoaDtoString = objectMapper.writeValueAsString(new PessoaDto(null, "Juca", "José", "2000-07-08", Arrays.asList("Java")));
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Apelido existente", errorResponse.message());
        assertEquals(422, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 422 por nome ser nulo")
    void postPessoas4() throws Exception {
        String pessoaDtoString = objectMapper.writeValueAsString(new PessoaDto(null, "Leão", null, "2000-07-08", Arrays.asList("Java")));
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Valor de campo nulo", errorResponse.message());
        assertEquals(422, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 422 por nascimento ser nulo")
    void postPessoas5() throws Exception {
        String pessoaDtoString = objectMapper.writeValueAsString(new PessoaDto(null, "Urso", "José", null, Arrays.asList("Java")));
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Valor de campo nulo", errorResponse.message());
        assertEquals(422, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 422 por nascimento ter formato errado")
    void postPessoas6() throws Exception {
        String pessoaDtoString = "{\"id\":null,\"apelido\":\"Lopes\",\"nome\":\"José\",\"nascimento\":\"20-12-2000\",\"stack\":null}";
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Nascimento em formato errado", errorResponse.message());
        assertEquals(422, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 400 por apelido ser numérico")
    void postPessoas7() throws Exception {
        String pessoaDtoString = "{\"id\":null,\"apelido\":1,\"nome\":\"José\",\"nascimento\":\"2000-07-08\",\"stack\":[\"Java\"]}";
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Valor numerico em vez de string", errorResponse.message());
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 400 por nome ser numérico")
    void postPessoas8() throws Exception {
        String pessoaDtoString = "{\"id\":null,\"apelido\":\"Barão\",\"nome\":1.1,\"nascimento\":\"2000-07-08\",\"stack\":[\"Java\"]}";
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Valor numerico em vez de string", errorResponse.message());
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 400 por nascimento ser numérico")
    void postPessoas9() throws Exception {
        String pessoaDtoString = "{\"id\":null,\"apelido\":\"Lopes\",\"nome\":\"José\",\"nascimento\":2000,\"stack\":[\"Java\"]}";
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Valor numerico em vez de string", errorResponse.message());
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 400 por nascimento ser um array")
    void postPessoas10() throws Exception {
        String pessoaDtoString = "{\"id\":null,\"apelido\":\"Lopes\",\"nome\":\"José\",\"nascimento\":[\"outro\"],\"stack\":null}";
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro 400 por stack possuir valor numérico")
    void postPessoas11() throws Exception {
        String pessoaDtoString = "{\"id\":null,\"apelido\":\"Lopes\",\"nome\":\"José\",\"nascimento\":[\"outro\"],\"stack\":[\"Spring\", 1]}";
        System.out.println(pessoaDtoString);

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        assertEquals(400, result.getResponse().getStatus());
    }
}
