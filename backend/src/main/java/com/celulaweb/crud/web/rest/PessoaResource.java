package com.celulaweb.crud.web.rest;

import com.celulaweb.crud.domain.Pessoa;
import com.celulaweb.crud.service.DocumentsService;
import com.celulaweb.crud.service.PessoaService;
import com.celulaweb.crud.service.dto.PessoaDTO;
import com.celulaweb.crud.service.dto.SearchPessoaDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/api/pessoas")
@RequiredArgsConstructor
public class PessoaResource {

    private final PessoaService pessoaService;
    private final DocumentsService documentsService;

    /*ex: http://localhost:8080/api/pessoas?filtro=Igor*/
    @GetMapping
    public ResponseEntity<Page<PessoaDTO>> listarPessoas(
            @ModelAttribute SearchPessoaDTO searchPessoaDTO, Pageable pageable){
        return new ResponseEntity<>(pessoaService.listarPessoas(searchPessoaDTO, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PessoaDTO> listarPessoaPorId(@PathVariable Long id){
        return new ResponseEntity<>(pessoaService.listarPessoaPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criarPessoa(@Valid @RequestBody Pessoa pessoa){
        return new ResponseEntity<>(pessoaService.criarPessoa(pessoa), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deletarPessoa(@PathVariable Long id){
        pessoaService.deletarPessoa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/put/{id}")
    public ResponseEntity<PessoaDTO> alterarPessoa(@Valid @RequestBody Pessoa pessoa){
        return new ResponseEntity<>(pessoaService.alterarPessoa(pessoa), HttpStatus.OK);
    }

    @GetMapping(path = "/pdf/{id}")
    public ResponseEntity<byte[]> gerarPDF(HttpServletResponse response, @PathVariable Long id) throws IOException {
        byte[] bytes = documentsService.thymeleafTemplate(id);
        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }

    @GetMapping(path = "/xls")
    public ResponseEntity<byte[]> gerarXLS(HttpServletResponse response) throws IOException {
        byte[] bytes = documentsService.xlsFile();
        return new ResponseEntity<>(bytes, HttpStatus.OK);

    }

}
