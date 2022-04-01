package com.celulaweb.crud.service.mappers;

import com.celulaweb.crud.domain.Cidade;
import com.celulaweb.crud.service.dto.CidadeDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface CidadeMapper {

    CidadeDTO cidadeParaDTO(Cidade cidade);
    List<CidadeDTO> cidadeParaDTO(List<Cidade> cidade);
    Cidade cidadeDTOParaCidade(CidadeDTO cidadeDTO);

}
