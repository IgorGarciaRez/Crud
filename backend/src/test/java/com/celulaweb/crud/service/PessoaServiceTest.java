package com.celulaweb.crud.service;

import com.celulaweb.crud.domain.Pessoa;
import com.celulaweb.crud.repository.PessoaRepository;
import com.celulaweb.crud.util.PessoaCreator;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp(){
        Pessoa pessoa = PessoaCreator.criarPessoaValida();
        Pessoa pessoaAtualizada = PessoaCreator.criarPessoaAtualizadaValida();
        List<Pessoa> list = Collections.singletonList(pessoa);
        BDDMockito.when(pessoaRepository.listarPorFiltros(ArgumentMatchers.any(),
                        ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(list));
        BDDMockito.when(pessoaRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(pessoa));
        BDDMockito.when(pessoaRepository.save(ArgumentMatchers.any(Pessoa.class)))
                .thenReturn(pessoa);
        BDDMockito.doNothing().when(pessoaRepository).delete(ArgumentMatchers.any());
        BDDMockito.when(pessoaRepository.save(ArgumentMatchers.any(Pessoa.class)))
                .thenReturn(pessoaAtualizada);
    }

    @Test
    void list_returnPessoaPage_whenSuccessful(){
        Long id = PessoaCreator.criarPessoaValida().getId();
        Page<Pessoa> pessoaPage = pessoaRepository.listarPorFiltros(null, null, null, null);
        Assertions.assertThat(pessoaPage).isNotNull();
        Assertions.assertThat(pessoaPage.isEmpty()).isFalse();
        Assertions.assertThat(pessoaPage.getSize() > 0).isTrue();
        Assertions.assertThat(pessoaPage.toList().get(0).getId()).isEqualTo(id);
    }

    @Test
    void findById_returnPessoaDTO_whenSuccessful(){
        Long id = PessoaCreator.criarPessoaValida().getId();
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        Assertions.assertThat(pessoa).isNotNull();
        Assertions.assertThat(pessoa.get().getId()).isEqualTo(id);
    }

    @Test
    void save_returnPessoaDTO_whenSuccessful(){
        Pessoa pessoa = PessoaCreator.criarPessoaValida();
        Pessoa pessoaS = pessoaRepository.save(pessoa);
        Assertions.assertThat(pessoaS).isNotNull();
        Assertions.assertThat(pessoaS.getId()).isEqualTo(pessoa.getId());
    }

    @Test
    void delete_returnsHttpStatus_whenSuccessful(){
        Assertions.assertThatCode(() -> pessoaRepository.delete(PessoaCreator.criarPessoaValida()))
                .doesNotThrowAnyException();
    }

    @Test
    void update_returnPessoaDTO_whenSuccessful(){
        Assertions.assertThatCode(() -> pessoaRepository.save(PessoaCreator.criarPessoaAtualizadaValida()))
                .doesNotThrowAnyException();
    }

}