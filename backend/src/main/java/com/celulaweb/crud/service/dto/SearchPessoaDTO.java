package com.celulaweb.crud.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SearchPessoaDTO implements Serializable {

    private String nome;
    private String cpf;
    private String cNome;

}
