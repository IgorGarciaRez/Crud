package com.celulaweb.crud.util;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.domain.Pessoa;

public class PessoaCreator {

    public static Pessoa criarPessoa2BSalva(){
        return Pessoa.builder()
                .nome("Roger")
                .cpf("54027892300")
                .apelido("Rogerinho")
                .timeCoracao("Flamengo")
                .hobbie("Futebol")
                .build();
    }

    public static Pessoa criarPessoaValida(){
        return Pessoa.builder()
                .id(1L)
                .nome("Roger")
                .cpf("54027892300")
                .apelido("Rogerinho")
                .timeCoracao("Flamengo")
                .hobbie("Futebol")
                .cidade(CidadeCreator.criarCidadeValida())
                .build();
    }

    public static Pessoa criarPessoaAtualizadaValida(){
        return Pessoa.builder()
                .id(1L)
                .nome("Rogerson")
                .cpf("54027892300")
                .apelido("Rogerinho")
                .timeCoracao("Flamengo")
                .hobbie("Futebol")
                .cidade(CidadeCreator.criarCidadeValida())
                .build();
    }

}
