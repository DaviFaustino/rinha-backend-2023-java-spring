package com.davifaustino.rinhabackend.application;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(value = "/pessoas/{id}")
    public ResponseEntity<PessoaDto> getOnePessoa(@PathVariable(value = "id") String id) {
        PessoaDto pessoaDto = pessoaService.getOnePessoa(id);

        return ResponseEntity.status(HttpStatus.OK).body(pessoaDto);
    }

    // O parâmetro t não é requerido no endpoint para que o domínio lide com a validação de não nulo
    @GetMapping(value = "/pessoas")
    public ResponseEntity<List<PessoaDto>> getPessoas(@RequestParam(required = false) String t) {
        List<PessoaDto> pessoaDtos = pessoaService.getPessoas(t);

        return ResponseEntity.status(HttpStatus.OK).body(pessoaDtos);
    }
}
