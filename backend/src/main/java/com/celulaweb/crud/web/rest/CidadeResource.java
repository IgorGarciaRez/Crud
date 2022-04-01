package com.celulaweb.crud.web.rest;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.service.CidadeService;
import com.celulaweb.crud.service.dto.CidadeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/api/cidades")
@RequiredArgsConstructor
public class CidadeResource {

    private final CidadeService cidadeService;

    /*ex: http://localhost:8080/api/cidades?filtro=formiga*/
    @GetMapping
    public ResponseEntity<Page<CidadeDTO>> listarCidades(
            @ModelAttribute CidadeDTO cidadeDTO, Pageable pageable){
        return new ResponseEntity<>(cidadeService.listarCidades(cidadeDTO, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CidadeDTO> listarCidadePorId(@PathVariable Long id){
        return new ResponseEntity<>(cidadeService.listarCidadePorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CidadeDTO> criarCidade(@Valid @RequestBody Cidade cidade) {
        return new ResponseEntity<>(cidadeService.criarCidade(cidade), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deletarCidade(@PathVariable Long id){
        cidadeService.deletarCidade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/put/{id}")
    public ResponseEntity<CidadeDTO> alterarCidade(@Valid @RequestBody Cidade cidade){
        return new ResponseEntity<>(cidadeService.alterarCidade(cidade), HttpStatus.OK);
    }

}
