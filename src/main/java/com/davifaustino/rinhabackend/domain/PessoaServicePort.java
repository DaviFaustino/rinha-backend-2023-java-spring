package com.davifaustino.rinhabackend.domain;

import java.util.UUID;

public interface PessoaServicePort {
    
    public UUID savePessoa(PessoaDto pessoaDto);
}
