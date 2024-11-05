package com.davifaustino.rinhabackend.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
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
import com.fasterxml.jackson.core.type.TypeReference;
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

        MvcResult result = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaDtoString))
                .andReturn();

        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Deve retornar com sucesso os detalhes de uma pessoa com status 200")
    void getDetalhesPessoa1() throws Exception {

        MvcResult result = mockMvc.perform(get("/pessoas/{id}", "3f306ba4-bcb1-4c7c-bfcb-78a747225eda")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        objectMapper.readValue(result.getResponse().getContentAsString(), PessoaDto.class);
    }

    @Test
    @DisplayName("Deve retornar um erro 404 pela não existência da pessoa")
    void getDetalhesPessoa2() throws Exception {

        mockMvc.perform(get("/pessoas/{id}", "a804947b-36a4-4625-9cf9-3ef51f2a8170")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Pessoa nao encontrada")));
    }

    @Test
    @DisplayName("Deve retornar as pessoas com sucesso com status 200")
    void getPessoas1() throws Exception {

        MvcResult result = mockMvc.perform(get("/pessoas")
                        .param("t", "java")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<PessoaDto> listaPessoas = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PessoaDto>>(){});
        assertTrue(listaPessoas.size() == 3);
    }

    @Test
    @DisplayName("Deve retornar as pessoas com sucesso com status 200")
    void getPessoas2() throws Exception {

        MvcResult result = mockMvc.perform(get("/pessoas")
                        .param("t", "dino")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<PessoaDto> listaPessoas = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PessoaDto>>(){});
        assertTrue(listaPessoas.size() == 1);
    }

    @Test
    @DisplayName("Deve retornar as pessoas com sucesso com status 200")
    void getPessoas3() throws Exception {

        MvcResult result = mockMvc.perform(get("/pessoas")
                        .param("t", "mat")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<PessoaDto> listaPessoas = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PessoaDto>>(){});
        assertTrue(listaPessoas.size() == 1);
    }

    @Test
    @DisplayName("Deve retornar json vazio com status 200")
    void getPessoas4() throws Exception {

        MvcResult result = mockMvc.perform(get("/pessoas")
                        .param("t", "dilma")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<PessoaDto> listaPessoas = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<PessoaDto>>(){});
        assertTrue(listaPessoas.size() == 0);
    }

    @Test
    @DisplayName("Deve retornar um erro com status 400 por t está em branco")
    void getPessoas5() throws Exception {

        MvcResult result = mockMvc.perform(get("/pessoas")
                        .param("t", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Termo em branco", errorResponse.message());
    }

    @Test
    @DisplayName("Deve retornar um erro com status 400 por t não ter sido informado")
    void getPessoas6() throws Exception {

        MvcResult result = mockMvc.perform(get("/pessoas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertEquals("Termo nulo", errorResponse.message());
    }
}
