package com.davifaustino.rinhabackend.domain;

import java.util.List;
import java.util.UUID;

public interface PessoaRepositoryPort {

    public boolean existsByApelido(String apelido);
    public UUID savePessoa(Pessoa pessoa);
    public Pessoa getOnePessoa(UUID id);
    public List<Pessoa> getPessoas(String termo);
    public int getPessoasCounting();
}
