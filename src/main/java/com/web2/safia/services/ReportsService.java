package com.web2.safia.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;
import org.springframework.stereotype.Service;
import com.web2.safia.report.JaspersoftUtil;

@Service
public class ReportsService {
    private final DataSource dataSource;
    private final JaspersoftUtil jaspersoftUtil;

    public ReportsService(DataSource dataSource, JaspersoftUtil jaspersoftUtil) {
        this.dataSource = dataSource;
        this.jaspersoftUtil = jaspersoftUtil;
    }

    // Opção 1: Todos os Projetos (Envia null nos parâmetros)
    public byte[] gerarRelatorioTodosProjetos() {
        return jaspersoftUtil.gerarRelatorio(
            "/reports/RelatorioProjetosMaster.jasper", 
            null, // Aqui o Jasper receberá parâmetros vazios e fará o 'IS NULL' 
            dataSource
        );
    }

    // Opção 2: Projeto Específico (Envia o ID)
    public byte[] gerarRelatorioProjetoPorId(UUID id) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID_PROJETO_FILTRO", id); // Envia o ID para filtrar
        
        return jaspersoftUtil.gerarRelatorio(
            "/reports/RelatorioProjetosMaster.jasper", 
            parametros, 
            dataSource
        );
    }
}
