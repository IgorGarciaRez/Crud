package com.celulaweb.crud.repository;

import com.celulaweb.crud.domain.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    @Query("select c from Cidade c where (:nome is null or c.nome like %:nome%) and (:estado is null or c.estado = :estado)")
    Page<Cidade> listarPorFiltros(@Param("nome") String nome, @Param("estado") String estado, Pageable pageable);

}
