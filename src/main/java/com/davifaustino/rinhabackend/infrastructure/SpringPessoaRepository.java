package com.davifaustino.rinhabackend.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringPessoaRepository extends JpaRepository<PessoaEntity, UUID> {
    
    boolean existsByApelido(String apelido);

    @Query(value = "SELECT * FROM tb_pessoas " +
                    "WHERE busca  ILIKE CONCAT('%', :termo, '%') " +
                    "LIMIT 50;", nativeQuery = true)
    List<PessoaEntity> findByBuscaIgnoreCaseContaining(@Param("termo") String termo);

    @Query(value = "SELECT COUNT(*) FROM tb_pessoas;", nativeQuery = true)
    int getPessoasCounting();
}
