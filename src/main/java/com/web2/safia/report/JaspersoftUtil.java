package com.web2.safia.report;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Component
public class JaspersoftUtil {
    public static byte[] generateReport(String jasperFile, Map<String, Object> parameters, DataSource dataSource) {
        try (var connection = dataSource.getConnection()) {
            var cpr = new ClassPathResource(jasperFile);
            var reportUrl = cpr.getURL().toString();
            var directoryReport = reportUrl.substring(0, reportUrl.lastIndexOf("/") + 1);

            // NOTE: Alinha com o parâmetro do Jasper
            parameters.put("SUBREPORT_DIR", directoryReport);

            JasperPrint jasperPrint = JasperFillManager.fillReport(cpr.getInputStream(), parameters, connection);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {

            return null;
        }
    }
}
