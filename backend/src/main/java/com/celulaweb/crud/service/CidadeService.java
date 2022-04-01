package com.celulaweb.crud.service;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.repository.CidadeRepository;
import com.celulaweb.crud.service.dto.CidadeDTO;
import com.celulaweb.crud.service.mappers.CidadeMapper;
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
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final CidadeMapper cidadeMapper;

    public Page<CidadeDTO> listarCidades(CidadeDTO cidadeDTO, Pageable pageable){
        return cidadeRepository.listarPorFiltros(cidadeDTO.getNome(), cidadeDTO.getEstado(), pageable).map(cidadeMapper::cidadeParaDTO);
    }

    public CidadeDTO listarCidadePorId(@PathVariable Long id){
        return cidadeMapper.cidadeParaDTO(cidadeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade nao encontrada")));
    }

    public CidadeDTO criarCidade(@RequestBody Cidade cidade){
        return cidadeMapper.cidadeParaDTO(cidadeRepository.save(cidade));
    }

    public void deletarCidade(@PathVariable Long id){
        this.listarCidadePorId(id);
        cidadeRepository.deleteById(id);
    }

    public CidadeDTO alterarCidade(@RequestBody Cidade cidade){
        return cidadeMapper.cidadeParaDTO(cidadeRepository.save(cidade));
    }

}
