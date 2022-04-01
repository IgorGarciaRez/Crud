package com.celulaweb.crud.service.mappers;

import com.celulaweb.crud.domain.Pessoa;
import com.celulaweb.crud.service.dto.PessoaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface PessoaMapper {

    @Mapping(source = "cidade", target = "cidadeDTO")
    PessoaDTO pessoaParaDTO(Pessoa pessoa);
    List<PessoaDTO> pessoaParaDTO(List<Pessoa> pessoa);
    Pessoa pessoaDTOParaPessoa(PessoaDTO pessoaDTO);

}
