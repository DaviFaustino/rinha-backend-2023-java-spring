package com.davifaustino.rinhabackend.domain;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<PessoaDto> getPessoas(String termo) {
        if (termo == null) throw new BadRequestException("Termo nulo");
        if (termo.isBlank()) throw new BadRequestException("Termo em branco");

        return pessoaRepository.getPessoas(termo).stream().map(pessoa -> pessoa.toPessoaDto()).collect(Collectors.toList());
    }

    @Override
    public int getPessoasCounting() {
        return pessoaRepository.getPessoasCounting();
    }
}
