package com.web2.safia.report;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

@Service
public class ReportsService {
    private final DataSource dataSource;

    public ReportsService(
            DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public byte[] generateAllProjectsReport() {
        return JaspersoftUtil
                .generateReport(
                        "/reports/RelatorioProjetosMaster.jasper",
                        new HashMap<>(),
                        dataSource);
    }

    public byte[] generateProjectReportById(UUID id) {
        Map<String, Object> parametros = new HashMap<>();
        // NOTE: Envia o ID para filtrar
        parametros.put("ID_PROJETO_FILTRO", id);

        return JaspersoftUtil
                .generateReport(
                        "/reports/RelatorioProjetosMaster.jasper",
                        parametros,
                        dataSource);
    }
}
