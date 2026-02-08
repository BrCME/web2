package com.web2.safia.report;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/reports/")
public class ReportController {
    private final ReportsService reportsService;

    public ReportController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping
    public ResponseEntity<byte[]> getAllProjectsReports() {
        byte[] report = reportsService.generateAllProjectsReport();
        return hidrateReportHeadersResponse(report, "All_Projects.pdf");
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getProjectReportsById(@PathVariable UUID id) {
        byte[] report = reportsService.generateProjectReportById(id);
        return hidrateReportHeadersResponse(report, "Project_".concat(id.toString()).concat(".pdf"));
    }

    private ResponseEntity<byte[]> hidrateReportHeadersResponse(byte[] content, String fileName) {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=".concat(fileName))
                .body(content);
    }
}