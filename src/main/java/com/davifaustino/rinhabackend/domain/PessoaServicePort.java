package com.davifaustino.rinhabackend.domain;

import java.util.List;
import java.util.UUID;

public interface PessoaServicePort {
    
    public UUID savePessoa(PessoaDto pessoaDto);
    public PessoaDto getOnePessoa(String id);
    public List<PessoaDto> getPessoas(String termo);
    public int getPessoasCounting();
}
