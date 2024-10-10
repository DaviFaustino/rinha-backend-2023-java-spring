package com.davifaustino.rinhabackend.application;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.davifaustino.rinhabackend.domain.PessoaDto;
import com.davifaustino.rinhabackend.domain.PessoaServicePort;

@RestController
public class PessoaController {

    private PessoaServicePort pessoaService;

    public PessoaController(PessoaServicePort pessoaService) {
        this.pessoaService = pessoaService;
    }
    
    @PostMapping(value = "/pessoas", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> savePessoa(@RequestBody PessoaDto pessoaDto) {
        UUID uuid = pessoaService.savePessoa(pessoaDto);

        return ResponseEntity.created(URI.create("/pessoas/" + uuid.toString())).body(uuid);
    }
}
