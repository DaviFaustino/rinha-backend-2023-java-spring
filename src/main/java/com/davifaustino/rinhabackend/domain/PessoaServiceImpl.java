package com.davifaustino.rinhabackend.domain;

import java.util.UUID;

public class PessoaServiceImpl implements PessoaServicePort {
    
    private PessoaRepositoryPort pessoaRepository;

    public PessoaServiceImpl(PessoaRepositoryPort pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public UUID savePessoa(PessoaDto pessoaDto) {
        if (!pessoaRepository.existsByApelido(pessoaDto.apelido())) {

            return pessoaRepository.savePessoa(new Pessoa(pessoaDto));
        } else {
            throw new UnprocessableException("Apelido existente");
        }
    }

    @Override
    public PessoaDto getOnePessoa(String id) {
        return pessoaRepository.getOnePessoa(UUID.fromString(id)).toPessoaDto();
    }
}
