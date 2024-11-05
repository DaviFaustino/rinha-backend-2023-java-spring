package com.davifaustino.rinhabackend.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Pessoa {
    
    private UUID id;
    private String apelido;
    private String nome;
    private String nascimento;
    private List<String> stack;
    private String busca;

    public Pessoa(UUID id, String apelido, String nome, String nascimento, List<String> stack) {
        this.id = id;
        this.apelido = apelido;
        this.nome = nome;
        this.nascimento = nascimento;
        this.stack = stack;
        this.busca = apelido + nome + String.join("", stack);
    }

    public Pessoa(PessoaDto pessoaDto) {
        validar(pessoaDto);

        this.apelido = pessoaDto.apelido();
        this.nome = pessoaDto.nome();
        this.nascimento = pessoaDto.nascimento();
        this.stack = pessoaDto.stack();
        this.busca = pessoaDto.apelido() + pessoaDto.nome() + String.join("", pessoaDto.stack());
        this.id = UUID.randomUUID();
    }

    private void validar(PessoaDto pessoaDto) {

        if (pessoaDto.apelido() == null || pessoaDto.nome() == null || pessoaDto.nascimento() == null) {
            throw new UnprocessableException("Valor de campo nulo");
        }
        if (pessoaDto.apelido().matches("[0-9.]+") || pessoaDto.nome().matches("[0-9.]+") || pessoaDto.nascimento().matches("[0-9.]+")) {
            throw new BadRequestException("Valor numerico em vez de string");
        }
        try {
            LocalDate.parse(pessoaDto.nascimento(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new UnprocessableException("Nascimento em formato errado");
        }
        if (pessoaDto.stack() != null && pessoaDto.stack().isEmpty()) {
            throw new BadRequestException("Stack vazia");
        }
    }

    public PessoaDto toPessoaDto() {
        return new PessoaDto(id, apelido, nome, nascimento, stack);
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

    public String getBusca() {
        return busca;
    }
}
