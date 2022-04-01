package com.celulaweb.crud.web.rest;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.service.CidadeService;
import com.celulaweb.crud.service.dto.CidadeDTO;
import com.celulaweb.crud.util.CidadeCreator;
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
class CidadeResourceTest {

    @InjectMocks
    private CidadeResource cidadeResource;

    @Mock
    private CidadeService cidadeService;

    @BeforeEach
    void setUp(){
        CidadeDTO cidadeDTO = new ModelMapper().map(CidadeCreator.criarCidadeValida(), CidadeDTO.class);
        CidadeDTO cidadeDTOAtualizada = new ModelMapper().map(CidadeCreator.criarCidadeAtualizadaValida(), CidadeDTO.class);
        List<CidadeDTO> list = Collections.singletonList(cidadeDTO);
        BDDMockito.when(cidadeService.listarCidades(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(list));
        BDDMockito.when(cidadeService.listarCidadePorId(ArgumentMatchers.any()))
                .thenReturn(cidadeDTO);
        BDDMockito.when(cidadeService.criarCidade(ArgumentMatchers.any(Cidade.class)))
                .thenReturn(cidadeDTO);
        BDDMockito.doNothing().when(cidadeService).deletarCidade(ArgumentMatchers.any());
        BDDMockito.when(cidadeService.alterarCidade(ArgumentMatchers.any(Cidade.class)))
                .thenReturn(cidadeDTOAtualizada);
    }

    @Test
    void list_returnCidadePage_whenSuccessful(){
        String nome = CidadeCreator.criarCidadeValida().getNome();
        Page<CidadeDTO> cidadeDTOPage = cidadeResource.listarCidades(null, null).getBody();
        Assertions.assertThat(cidadeDTOPage).isNotNull();
        Assertions.assertThat(cidadeDTOPage.isEmpty()).isFalse();
        Assertions.assertThat(cidadeDTOPage.getSize() > 0).isTrue();
        Assertions.assertThat(cidadeDTOPage.toList().get(0).getNome()).isEqualTo(nome);
    }

    @Test
    void listId_returnCidadeDTO_whenSuccessful(){
        Long id = CidadeCreator.criarCidadeValida().getId();
        CidadeDTO cidadeDTO = cidadeResource.listarCidadePorId(id).getBody();
        Assertions.assertThat(cidadeDTO).isNotNull();
        Assertions.assertThat(cidadeDTO.getId()).isEqualTo(id);
    }

    @Test
    void save_returnCidadeDTO_whenSuccessful(){
        Cidade cidade = CidadeCreator.criarCidadeValida();
        CidadeDTO cidadeDTO = cidadeResource.criarCidade(cidade).getBody();
        Assertions.assertThat(cidadeDTO).isNotNull();
        Assertions.assertThat(cidadeDTO.getId()).isEqualTo(cidade.getId());
    }

    @Test
    void delete_returnsHttpStatus_whenSuccessful(){
        Assertions.assertThatCode(() -> cidadeResource.deletarCidade(CidadeCreator.criarCidadeValida().getId()))
                .doesNotThrowAnyException();
        ResponseEntity<HttpStatus> status = cidadeResource.deletarCidade(CidadeCreator.criarCidadeValida().getId());
        Assertions.assertThat(status).isNotNull();
        Assertions.assertThat(status.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void update_returnCidadeDTO_whenSuccessful(){
        Cidade cidade = CidadeCreator.criarCidadeAtualizadaValida();
        CidadeDTO cidadeDTO = cidadeResource.alterarCidade(cidade).getBody();
        Assertions.assertThat(cidadeDTO).isNotNull();
        Assertions.assertThat(cidadeDTO.getId()).isEqualTo(cidade.getId());
    }

}