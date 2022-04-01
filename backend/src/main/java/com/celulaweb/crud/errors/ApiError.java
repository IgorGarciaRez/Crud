package com.celulaweb.crud.errors;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiError {

    private HttpStatus status;
    private String mensagem;
    private List<String> erros;

    public ApiError(HttpStatus status, String mensagem, List<String> erros){
        super();
        this.status = status;
        this.mensagem = mensagem;
        this.erros = erros;
    }

    public ApiError(HttpStatus status, String mensagem, String erro){
        super();
        this.status = status;
        this.mensagem = mensagem;
        this.erros = Arrays.asList(erro);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getError() {
        return erros;
    }

    public void setError(List<String> erros) {
        this.erros = erros;
    }
}
