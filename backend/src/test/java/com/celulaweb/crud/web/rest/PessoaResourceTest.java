package com.celulaweb.crud.web.rest;

import com.celulaweb.crud.domain.Pessoa;
import com.celulaweb.crud.service.PessoaService;
import com.celulaweb.crud.service.dto.PessoaDTO;
import com.celulaweb.crud.util.PessoaCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Log4j2
class PessoaResourceTest {

    @InjectMocks
    private PessoaResource pessoaResource;

    @Mock
    private PessoaService pessoaService;

    @BeforeEach
    void setUp(){
        PessoaDTO pessoaDTO = new ModelMapper().map(PessoaCreator.criarPessoaValida(), PessoaDTO.class);
        PessoaDTO pessoaDTOAtualizada = new ModelMapper().map(PessoaCreator.criarPessoaAtualizadaValida(), PessoaDTO.class);
        List<PessoaDTO> list = Collections.singletonList(pessoaDTO);
        BDDMockito.when(pessoaService.listarPessoas(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(list));
        BDDMockito.when(pessoaService.listarPessoaPorId(ArgumentMatchers.any()))
                .thenReturn(pessoaDTO);
        BDDMockito.when(pessoaService.criarPessoa(ArgumentMatchers.any(Pessoa.class)))
                .thenReturn(pessoaDTO);
        BDDMockito.doNothing().when(pessoaService).deletarPessoa(ArgumentMatchers.any());
        BDDMockito.when(pessoaService.alterarPessoa(ArgumentMatchers.any(Pessoa.class)))
                .thenReturn(pessoaDTOAtualizada);
    }

    @Test
    void list_returnPessoaPage_whenSuccessful(){
        String nome = PessoaCreator.criarPessoaValida().getNome();
        Page<PessoaDTO> pessoaDTOPage = pessoaResource.listarPessoas(null, null).getBody();
        Assertions.assertThat(pessoaDTOPage).isNotNull();
        Assertions.assertThat(pessoaDTOPage.isEmpty()).isFalse();
        Assertions.assertThat(pessoaDTOPage.getSize() > 0).isTrue();
        Assertions.assertThat(pessoaDTOPage.toList().get(0).getNome()).isEqualTo(nome);
    }

    @Test
    void listId_returnPessoaDTO_whenSuccessful(){
        Long id = PessoaCreator.criarPessoaValida().getId();
        PessoaDTO pessoaDTO = pessoaResource.listarPessoaPorId(id).getBody();
        Assertions.assertThat(pessoaDTO).isNotNull();
        Assertions.assertThat(pessoaDTO.getId()).isEqualTo(id);
    }

    @Test
    void save_returnPessoaDTO_whenSuccessful(){
        Pessoa pessoa = PessoaCreator.criarPessoaValida();
        PessoaDTO pessoaDTO = pessoaResource.criarPessoa(pessoa).getBody();
        Assertions.assertThat(pessoaDTO).isNotNull();
        Assertions.assertThat(pessoaDTO.getId()).isEqualTo(pessoa.getId());
    }

    @Test
    void delete_returnsHttpStatus_whenSuccessful(){
        Assertions.assertThatCode(() -> pessoaResource.deletarPessoa(PessoaCreator.criarPessoaValida().getId()))
                .doesNotThrowAnyException();
        ResponseEntity<HttpStatus> status = pessoaResource.deletarPessoa(PessoaCreator.criarPessoaValida().getId());
        Assertions.assertThat(status).isNotNull();
        Assertions.assertThat(status.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void update_returnPessoaDTO_whenSuccessful(){
        Pessoa pessoa = PessoaCreator.criarPessoaAtualizadaValida();
        PessoaDTO pessoaDTO = pessoaResource.alterarPessoa(pessoa).getBody();
        Assertions.assertThat(pessoaDTO).isNotNull();
        Assertions.assertThat(pessoaDTO.getId()).isEqualTo(pessoa.getId());
    }

}