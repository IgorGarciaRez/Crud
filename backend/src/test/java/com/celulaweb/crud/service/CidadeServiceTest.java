package com.celulaweb.crud.service;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.repository.CidadeRepository;
import com.celulaweb.crud.util.CidadeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class CidadeServiceTest {

    @InjectMocks
    private CidadeService cidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    @BeforeEach
    void setUp(){
        Cidade cidade = CidadeCreator.criarCidadeValida();
        Cidade cidadeAtualizada = CidadeCreator.criarCidadeAtualizadaValida();
        List<Cidade> list = Collections.singletonList(cidade);
        BDDMockito.when(cidadeRepository.listarPorFiltros(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(list));
        BDDMockito.when(cidadeRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(cidade));
        BDDMockito.when(cidadeRepository.save(ArgumentMatchers.any(Cidade.class)))
                .thenReturn(cidade);
        BDDMockito.doNothing().when(cidadeRepository).delete(ArgumentMatchers.any());
        BDDMockito.when(cidadeRepository.save(ArgumentMatchers.any(Cidade.class)))
                .thenReturn(cidadeAtualizada);
    }

    @Test
    void list_returnCidadePage_whenSuccessful(){
        Long id = CidadeCreator.criarCidadeValida().getId();
        Page<Cidade> cidadePage = cidadeRepository.listarPorFiltros(null, null, null);
        Assertions.assertThat(cidadePage).isNotNull();
        Assertions.assertThat(cidadePage.isEmpty()).isFalse();
        Assertions.assertThat(cidadePage.getSize() > 0).isTrue();
        Assertions.assertThat(cidadePage.toList().get(0).getId()).isEqualTo(id);
    }

    @Test
    void findById_returnCidadeDTO_whenSuccessful(){
        Long id = CidadeCreator.criarCidadeValida().getId();
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        Assertions.assertThat(cidade).isNotNull();
        Assertions.assertThat(cidade.get().getId()).isEqualTo(id);
    }

    @Test
    void save_returnCidadeDTO_whenSuccessful(){
        Cidade cidade = CidadeCreator.criarCidadeValida();
        Cidade cidadeS = cidadeRepository.save(cidade);
        Assertions.assertThat(cidadeS).isNotNull();
        Assertions.assertThat(cidadeS.getId()).isEqualTo(cidade.getId());
    }

    @Test
    void delete_returnsHttpStatus_whenSuccessful(){
        Assertions.assertThatCode(() -> cidadeRepository.delete(CidadeCreator.criarCidadeValida()))
                .doesNotThrowAnyException();
    }

    @Test
    void update_returnCidadeDTO_whenSuccessful(){
        Assertions.assertThatCode(() -> cidadeRepository.save(CidadeCreator.criarCidadeAtualizadaValida()))
                .doesNotThrowAnyException();
    }

}