package com.davifaustino.rinhabackend.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringPessoaRepository extends JpaRepository<PessoaEntity, UUID> {
    
    boolean existsByApelido(String apelido);
}
