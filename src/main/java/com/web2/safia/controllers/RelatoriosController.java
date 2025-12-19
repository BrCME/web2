package com.web2.safia.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.services.ReportsService;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {
    
    @Autowired
    private ReportsService relatorioService;

    // URL: /relatorios/projetos/todos
    @GetMapping("/projetos/todos")
    public ResponseEntity<byte[]> baixarTodos() {
        byte[] pdf = relatorioService.gerarRelatorioTodosProjetos();
        return criarResponsePdf(pdf, "Todos_Projetos.pdf");
    }

    // URL: /relatorios/projeto/UUID-AQUI
    @GetMapping("/projeto/{id}")
    public ResponseEntity<byte[]> baixarUm(@PathVariable UUID id) {
        byte[] pdf = relatorioService.gerarRelatorioProjetoPorId(id);
        return criarResponsePdf(pdf, "Projeto_" + id + ".pdf");
    }

    // Método auxiliar para evitar repetição de código de cabeçalho [cite: 610-613]
    private ResponseEntity<byte[]> criarResponsePdf(byte[] conteudo, String nomeArquivo) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nomeArquivo)
            .body(conteudo);
    }
}