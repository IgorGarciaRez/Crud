package com.celulaweb.crud.repository;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.domain.Pessoa;
import com.celulaweb.crud.util.CidadeCreator;
import com.celulaweb.crud.util.PessoaCreator;
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
class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private CidadeRepository cidadeRepository;

    @Test
    void save_whenSuccessful(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        pessoa2BSalva.setCidade(cidadeSalva);
        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa2BSalva);
        Assertions.assertThat(pessoaSalva).isNotNull();
        Assertions.assertThat(pessoaSalva.getId()).isNotNull();
    }

    @Test
    void update_whenSuccessful(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        pessoa2BSalva.setCidade(cidadeSalva);
        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa2BSalva);
        pessoaSalva.setNome("Rogerio");
        Pessoa pessoaAtualizada = this.pessoaRepository.save(pessoaSalva);
        Assertions.assertThat(pessoaAtualizada).isNotNull();
        Assertions.assertThat(pessoaAtualizada.getId()).isNotNull();
        Assertions.assertThat(pessoaAtualizada.getNome()).isEqualTo(pessoaSalva.getNome());
    }

    @Test
    void delete_whenSuccessful(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        pessoa2BSalva.setCidade(cidadeSalva);
        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa2BSalva);
        this.pessoaRepository.delete(pessoaSalva);
        Optional<Pessoa> pessoaOptional = this.pessoaRepository.findById(pessoaSalva.getId());
        Assertions.assertThat(pessoaOptional.isPresent()).isFalse();
    }

    @Test
    void findByFiltros_whenSuccessful(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        pessoa2BSalva.setCidade(cidadeSalva);
        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa2BSalva);
        Page<Pessoa> pessoas = this.pessoaRepository.listarPorFiltros(pessoaSalva.getNome(), null, null, null);
        Assertions.assertThat(pessoas.isEmpty()).isFalse();
        Assertions.assertThat(pessoas.toList()).contains(pessoaSalva);
    }

    @Test
    void findByFiltros_returnsListaVazia_whenPessoaNaoEncontrada(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        pessoa2BSalva.setCidade(cidadeSalva);
        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa2BSalva);
        Page<Pessoa> pessoas = this.pessoaRepository.listarPorFiltros("xaxa", null, null, null);
        Assertions.assertThat(pessoas.isEmpty()).isTrue();
    }

    @Test
    void save_throwError_whenPropertyIsEmpty(){
        Pessoa pessoa2BSalva = new Pessoa();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.pessoaRepository.save(pessoa2BSalva))
                .withMessageContaining("Não pode ser vazio");
    }



}