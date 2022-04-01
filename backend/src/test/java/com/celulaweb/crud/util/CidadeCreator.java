package com.celulaweb.crud.util;

import com.celulaweb.crud.domain.Cidade;

public class CidadeCreator {

    public static Cidade criarCidade2BSalva(){
        return Cidade.builder()
                .nome("Formiga")
                .estado("MG")
                .qtdHabitantes(75000).build();
    }

    public static Cidade criarCidadeValida(){
        return Cidade.builder()
                .id(1L)
                .nome("Formiga")
                .estado("MG")
                .qtdHabitantes(75000).build();
    }

    public static Cidade criarCidadeAtualizadaValida(){
        return Cidade.builder()
                .id(1L)
                .nome("Formiga 2")
                .estado("MG")
                .qtdHabitantes(75000).build();
    }

}
