package com.celulaweb.crud.integration;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.domain.Pessoa;
import com.celulaweb.crud.repository.CidadeRepository;
import com.celulaweb.crud.repository.PessoaRepository;
import com.celulaweb.crud.service.dto.PessoaDTO;
import com.celulaweb.crud.util.CidadeCreator;
import com.celulaweb.crud.util.PessoaCreator;
import com.celulaweb.crud.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PessoaResourceIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private CidadeRepository cidadeRepository;

    @Test
    void list_returnPessoaPage_whenSuccessful(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());;
        pessoa2BSalva.setCidade(cidadeSalva);
        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa2BSalva);
        String nome = pessoaSalva.getNome();
        PageableResponse<PessoaDTO> pessoaDTOPage = testRestTemplate.exchange("/api/pessoas", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<PessoaDTO>>() {
                }).getBody();
        Assertions.assertThat(pessoaDTOPage).isNotNull();
        Assertions.assertThat(pessoaDTOPage.isEmpty()).isFalse();
        Assertions.assertThat(pessoaDTOPage.getSize() > 0).isTrue();
        Assertions.assertThat(pessoaDTOPage.toList().get(0).getNome()).isEqualTo(nome);
    }

    @Test
    void listId_returnPessoa_whenSuccessful(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        pessoa2BSalva.setCidade(cidadeSalva);
        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa2BSalva);
        Long id = pessoaSalva.getId();
        PessoaDTO pessoa = testRestTemplate.getForObject("/api/pessoas/{id}", PessoaDTO.class, id);
        Assertions.assertThat(pessoa).isNotNull();
        Assertions.assertThat(pessoa.getId()).isEqualTo(id);
    }

    @Test
    void save_returnPessoa_whenSuccessful(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());;
        pessoa2BSalva.setCidade(cidadeSalva);
        ResponseEntity<PessoaDTO> pessoaResponseEntity = testRestTemplate.postForEntity("/api/pessoas", pessoa2BSalva, PessoaDTO.class);
        Assertions.assertThat(pessoaResponseEntity).isNotNull();
        Assertions.assertThat(pessoaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(pessoaResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(pessoaResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    void delete_returnsHttpStatus_whenSuccessful(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());;
        pessoa2BSalva.setCidade(cidadeSalva);
        Pessoa pessoa = this.pessoaRepository.save(pessoa2BSalva);
        ResponseEntity<Void> exchange = testRestTemplate.exchange("/api/pessoas/delete/{id}",
                HttpMethod.DELETE, null, Void.class, pessoa.getId());
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void update_returnPessoa_whenSuccessful(){
        Pessoa pessoa2BSalva = PessoaCreator.criarPessoa2BSalva();
        Cidade cidadeSalva = this.cidadeRepository.save(CidadeCreator.criarCidade2BSalva());;
        pessoa2BSalva.setCidade(cidadeSalva);
        Pessoa pessoa = this.pessoaRepository.save(pessoa2BSalva);
        pessoa.setNome("Rogerio");
        ResponseEntity<PessoaDTO> exchange = testRestTemplate.exchange("/api/pessoas/put/{id}",
                HttpMethod.PUT, new HttpEntity<>(pessoa), PessoaDTO.class, pessoa.getId());
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
