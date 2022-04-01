package com.celulaweb.crud.repository;

import com.celulaweb.crud.domain.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("select p from Pessoa p where (:nome is null or p.nome like %:nome%) and (:cpf is null or p.cpf = :cpf) and (:cidadeNome is null or p.cidade.nome like %:cidadeNome%)")
    Page<Pessoa> listarPorFiltros(@Param("nome") String nome, @Param("cpf") String cpf, @Param("cidadeNome") String cidadeNome, Pageable pageable);

}
