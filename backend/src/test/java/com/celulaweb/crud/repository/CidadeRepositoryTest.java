package com.celulaweb.crud.repository;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.util.CidadeCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@Log4j2
class CidadeRepositoryTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Test
    void save_whenSuccessful(){
        Cidade cidade2BSalva = CidadeCreator.criarCidade2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(cidade2BSalva);
        Assertions.assertThat(cidadeSalva).isNotNull();
        Assertions.assertThat(cidadeSalva.getId()).isNotNull();
    }

    @Test
    void update_whenSuccessful(){
        Cidade cidade2BSalva = CidadeCreator.criarCidade2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(cidade2BSalva);
        cidadeSalva.setQtdHabitantes(78000);
        Cidade cidadeAtualizada = this.cidadeRepository.save(cidadeSalva);
        Assertions.assertThat(cidadeAtualizada).isNotNull();
        Assertions.assertThat(cidadeAtualizada.getId()).isNotNull();
        Assertions.assertThat(cidadeAtualizada.getQtdHabitantes()).isEqualTo(cidadeSalva.getQtdHabitantes());
    }

    @Test
    void delete_whenSuccessful(){
        Cidade cidade2BSalva = CidadeCreator.criarCidade2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(cidade2BSalva);
        this.cidadeRepository.delete(cidadeSalva);
        Optional<Cidade> cidadeOptional = this.cidadeRepository.findById(cidadeSalva.getId());
        Assertions.assertThat(cidadeOptional.isPresent()).isFalse();
    }

    @Test
    void findByFiltros_whenSuccessful(){
        Cidade cidade2BSalva = CidadeCreator.criarCidade2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(cidade2BSalva);
        Page<Cidade> cidades = this.cidadeRepository.listarPorFiltros(cidadeSalva.getNome(), cidadeSalva.getEstado(), null);
        Assertions.assertThat(cidades.isEmpty()).isFalse();
        Assertions.assertThat(cidades.toList()).contains(cidadeSalva);
    }

    @Test
    void findByFiltros_returnsListaVazia_whenCidadeNaoEncontrada(){
        Cidade cidade2BSalva = CidadeCreator.criarCidade2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(cidade2BSalva);
        Page<Cidade> cidades = this.cidadeRepository.listarPorFiltros("xaxa", cidadeSalva.getEstado(), null);
        Assertions.assertThat(cidades.isEmpty()).isTrue();
    }

    @Test
    void save_throwError_whenPropertyIsEmpty(){
        Cidade cidade2BSalva = new Cidade();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.cidadeRepository.save(cidade2BSalva))
                .withMessageContaining("Não pode ser vazio");
    }



}