package com.davifaustino.rinhabackend.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.davifaustino.rinhabackend.domain.PessoaRepositoryPort;
import com.davifaustino.rinhabackend.domain.PessoaServiceImpl;
import com.davifaustino.rinhabackend.domain.PessoaServicePort;

@Configuration
public class BeanConfiguration {

    @Bean
    public PessoaServicePort pessoaServicePort(PessoaRepositoryPort pessoaRepositoryPort) {
        return new PessoaServiceImpl(pessoaRepositoryPort);
    }
}
