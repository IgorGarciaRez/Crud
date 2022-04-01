package com.celulaweb.crud.service;


import com.celulaweb.crud.domain.Pessoa;
import com.celulaweb.crud.repository.PessoaRepository;
import com.celulaweb.crud.service.dto.PessoaDTO;
import com.celulaweb.crud.service.dto.SearchPessoaDTO;
import com.celulaweb.crud.service.mappers.PessoaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;


    /*Pesquisar pessoas por nome, cpf ou cidade*/
    public Page<PessoaDTO> listarPessoas(SearchPessoaDTO searchPessoaDTO, Pageable pageable){
        return pessoaRepository.listarPorFiltros(searchPessoaDTO.getNome(), searchPessoaDTO.getCpf(), searchPessoaDTO.getCNome(), pageable).map(pessoaMapper::pessoaParaDTO);
    }

    public PessoaDTO listarPessoaPorId(@PathVariable Long id){
        return pessoaMapper.pessoaParaDTO(pessoaRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa não encontrada")));
    }

    public PessoaDTO criarPessoa(@RequestBody Pessoa pessoa){
        return pessoaMapper.pessoaParaDTO(pessoaRepository.save(pessoa));
    }

    public void deletarPessoa(@PathVariable Long id){
        this.listarPessoaPorId(id);
        pessoaRepository.deleteById(id);
    }

    public PessoaDTO alterarPessoa(@RequestBody Pessoa pessoa){
        return pessoaMapper.pessoaParaDTO(pessoaRepository.save(pessoa));
    }

}
