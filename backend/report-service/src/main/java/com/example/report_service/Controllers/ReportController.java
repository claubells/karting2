package com.example.report_service.Controllers;

import com.example.report_service.dto.ReportResponseDTO;
import com.example.report_service.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/report")
@CrossOrigin("*")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/turns")
    public ResponseEntity<ReportResponseDTO> getTurnsReport() {
        return ResponseEntity.ok(reportService.generateReportByTurns());
    }

    @GetMapping("/people")
    public ResponseEntity<ReportResponseDTO> getPeopleReport() {
        return ResponseEntity.ok(reportService.generateReportByPeople());
    }

}