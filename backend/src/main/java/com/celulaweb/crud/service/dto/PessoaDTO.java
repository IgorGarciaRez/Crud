package com.celulaweb.crud.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PessoaDTO implements Serializable {

    private Long id;
    private String nome;
    private String cpf;
    private String apelido;
    private String timeCoracao;
    private String hobbie;
    private CidadeDTO cidadeDTO;

}
