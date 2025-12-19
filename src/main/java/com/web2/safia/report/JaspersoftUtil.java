package com.web2.safia.report;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Component
public class JaspersoftUtil {
    public byte[] gerarRelatorio(String arquivoJasper, Map<String, Object> parametros, DataSource dataSource) {
        try (Connection conexao = dataSource.getConnection()) {
            ClassPathResource cpr = new ClassPathResource(arquivoJasper);
            String urlRelatorio = cpr.getURL().toString();
            String diretorioRelatorios = urlRelatorio.substring(0, urlRelatorio.lastIndexOf("/") + 1);
            
            if (parametros == null) parametros = new HashMap<>();
            parametros.put("SUBREPORT_DIR", diretorioRelatorios); // Alinha com o parâmetro do Jasper
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(cpr.getInputStream(), parametros, conexao);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            
            return null;
        }
    }
}
