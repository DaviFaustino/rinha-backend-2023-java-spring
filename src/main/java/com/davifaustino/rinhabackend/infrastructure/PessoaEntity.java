package com.davifaustino.rinhabackend.infrastructure;

import java.util.List;
import java.util.UUID;

import com.davifaustino.rinhabackend.domain.Pessoa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_pessoas")
@NoArgsConstructor
public class PessoaEntity {
    
    @Id
    private UUID id;
    private String apelido;
    private String nome;
    private String nascimento;
    private List<String> stack;

    public PessoaEntity(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.apelido = pessoa.getApelido();
        this.nome = pessoa.getNome();
        this.nascimento = pessoa.getNascimento();
        this.stack = pessoa.getStack();
    }

    public Pessoa toPessoa() {
        return new Pessoa(id, apelido, nome, nascimento, stack);
    }
}
