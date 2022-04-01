package com.celulaweb.crud.integration;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.repository.CidadeRepository;
import com.celulaweb.crud.service.dto.CidadeDTO;
import com.celulaweb.crud.util.CidadeCreator;
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
public class CidadeResourceIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Test
    void list_returnCidadePage_whenSuccessful(){
        Cidade cidadeSalva = cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        String nome = cidadeSalva.getNome();
        PageableResponse<CidadeDTO> cidadeDTOPage = testRestTemplate.exchange("/api/cidades", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<CidadeDTO>>() {
                }).getBody();
        Assertions.assertThat(cidadeDTOPage).isNotNull();
        Assertions.assertThat(cidadeDTOPage.isEmpty()).isFalse();
        Assertions.assertThat(cidadeDTOPage.getSize() > 0).isTrue();
        Assertions.assertThat(cidadeDTOPage.toList().get(0).getNome()).isEqualTo(nome);
    }

    @Test
    void listId_returnCidade_whenSuccessful(){
        Cidade cidadeSalva = cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        Long id = cidadeSalva.getId();
        Cidade cidade = testRestTemplate.getForObject("/api/cidades/{id}", Cidade.class, id);
        Assertions.assertThat(cidade).isNotNull();
        Assertions.assertThat(cidade.getId()).isEqualTo(id);
    }

    @Test
    void save_returnCidade_whenSuccessful(){
        Cidade cidade = CidadeCreator.criarCidade2BSalva();
        ResponseEntity<Cidade> cidadeResponseEntity = testRestTemplate.postForEntity("/api/cidades", cidade, Cidade.class);
        Assertions.assertThat(cidadeResponseEntity).isNotNull();
        Assertions.assertThat(cidadeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(cidadeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(cidadeResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    void delete_returnsHttpStatus_whenSuccessful(){
        Cidade cidade = cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        ResponseEntity<Void> exchange = testRestTemplate.exchange("/api/cidades/delete/{id}",
                HttpMethod.DELETE, null, Void.class, cidade.getId());
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void update_returnCidade_whenSuccessful(){
        Cidade cidade = cidadeRepository.save(CidadeCreator.criarCidade2BSalva());
        cidade.setNome("Arcos");
        ResponseEntity<CidadeDTO> exchange = testRestTemplate.exchange("/api/cidades/put/{id}",
                HttpMethod.PUT, new HttpEntity<>(cidade), CidadeDTO.class, cidade.getId());
        Assertions.assertThat(exchange).isNotNull();
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
