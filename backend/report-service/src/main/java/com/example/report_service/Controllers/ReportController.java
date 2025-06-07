package com.example.report_service.Controllers;

import com.example.reportservice.dto.ReportResponseDTO;
import com.example.reportservice.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    public ResponseEntity<ReportResponseDTO> getResumen() {
        return ResponseEntity.ok(reportService.generateSummary());
    }
}