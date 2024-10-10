package com.davifaustino.rinhabackend.domain;

import java.util.List;
import java.util.UUID;

public class Pessoa {
    
    private UUID id;
    private String apelido;
    private String nome;
    private String nascimento;
    private List<String> stack;

    public Pessoa(UUID id, String apelido, String nome, String nascimento, List<String> stack) {
        this.id = id;
        this.apelido = apelido;
        this.nome = nome;
        this.nascimento = nascimento;
        this.stack = stack;
    }

    public Pessoa(PessoaDto pessoaDto) {
        
        if (pessoaDto.apelido() == null || pessoaDto.nome() == null || pessoaDto.nascimento() == null) {
            throw new UnprocessableException("Valor nulo");
        }
        if (pessoaDto.stack() != null && pessoaDto.stack().isEmpty()) {
            throw new BadRequestException("Stack vazia");
        }

        this.apelido = pessoaDto.apelido();
        this.nome = pessoaDto.nome();
        this.nascimento = pessoaDto.nascimento();
        this.stack = pessoaDto.stack();
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getApelido() {
        return apelido;
    }

    public String getNome() {
        return nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public List<String> getStack() {
        return stack;
    }
}
