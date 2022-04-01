package com.celulaweb.crud.service;

import com.celulaweb.crud.service.dto.PessoaDTO;
import com.celulaweb.crud.service.dto.SearchPessoaDTO;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class DocumentsService {

    private final PessoaService pessoaService;

    public byte[] thymeleafTemplate(Long id) throws IOException {
        PessoaDTO pessoaDTO = pessoaService.listarPessoaPorId(id);
        if(pessoaDTO != null){
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode(TemplateMode.HTML);
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
            Context context = new Context();
            context.setVariable("id", pessoaDTO.getId());
            context.setVariable("nome", pessoaDTO.getNome());
            context.setVariable("cpf", pessoaDTO.getCpf());
            context.setVariable("apelido", pessoaDTO.getApelido());
            context.setVariable("timeCoracao", pessoaDTO.getTimeCoracao());
            context.setVariable("hobbie", pessoaDTO.getHobbie());
            context.setVariable("cidade", pessoaDTO.getCidadeDTO().getNome());
            String html = templateEngine.process("thymeleaf_template", context);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ConverterProperties properties = new ConverterProperties();
            properties.setBaseUri("http://localhost:8080");
            HtmlConverter.convertToPdf(html, outputStream, properties);
            byte[] bytes = outputStream.toByteArray();
            return bytes;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public byte[] xlsFile() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        List<PessoaDTO> listaPessoas = pessoaService.listarPessoas(new SearchPessoaDTO(), null).toList();
        if(listaPessoas.size() > 0) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Pessoas");
            sheet.setColumnWidth(0, 2000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 5000);
            criarHeader(workbook, sheet);

            for (int i = 0; i < listaPessoas.size(); i++) {
                PessoaDTO pessoa = listaPessoas.get(i);
                CellStyle style = workbook.createCellStyle();
                style.setWrapText(true);
                style.setAlignment(HorizontalAlignment.LEFT);
                Row row = sheet.createRow(i+1);
                criarCellPessoa(pessoa, style, row);
            }
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }



    /*CRIAÇÃO DE TABELA XLS
    *   | | | | |
    *   V V V V V
    * */

    private void criarCellPessoa(PessoaDTO pessoa, CellStyle style, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(pessoa.getId());
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(pessoa.getNome());
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue(pessoa.getCpf());
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue(pessoa.getApelido());
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue(pessoa.getTimeCoracao());
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue(pessoa.getHobbie());
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue(pessoa.getCidadeDTO().getNome());
        cell.setCellStyle(style);
    }

    private void criarHeader(Workbook workbook, Sheet sheet) {
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);
        header.setRowStyle(headerStyle);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("ID");
        headerCell = header.createCell(1);
        headerCell.setCellValue("Nome");
        headerCell = header.createCell(2);
        headerCell.setCellValue("CPF");
        headerCell = header.createCell(3);
        headerCell.setCellValue("Apelido");
        headerCell = header.createCell(4);
        headerCell.setCellValue("Time do Coração");
        headerCell = header.createCell(5);
        headerCell.setCellValue("Hobbie");
        headerCell = header.createCell(6);
        headerCell.setCellValue("Cidade");
    }

}
