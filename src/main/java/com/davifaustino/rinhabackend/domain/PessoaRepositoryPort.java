package com.davifaustino.rinhabackend.domain;

import java.util.UUID;

public interface PessoaRepositoryPort {

    public boolean existsByApelido(String apelido);
    public UUID savePessoa(Pessoa pessoa);
}
