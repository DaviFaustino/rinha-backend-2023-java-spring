package com.davifaustino.rinhabackend.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.davifaustino.rinhabackend.domain.NotFoundException;
import com.davifaustino.rinhabackend.domain.Pessoa;
import com.davifaustino.rinhabackend.domain.PessoaRepositoryPort;

import java.util.UUID;

@Component
public class PessoaRepository implements PessoaRepositoryPort {

    @Autowired
    private SpringPessoaRepository springPessoaRepository;

    @Override
    public boolean existsByApelido(String apelido) {
        return springPessoaRepository.existsByApelido(apelido);
    }

    @Override
    public UUID savePessoa(Pessoa pessoa) {
        springPessoaRepository.save(new PessoaEntity(pessoa));
        
        return pessoa.getId();
    }

    @Override
    public Pessoa getOnePessoa(UUID id) {
        PessoaEntity pessoaEntity = springPessoaRepository.findById(id).orElseThrow(() -> new NotFoundException("Pessoa nao encontrada"));

        return pessoaEntity.toPessoa();
    }
}
