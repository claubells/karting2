package com.example.report_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ReportResponseDTO {
    private Map<String, Map<String, Long>> turns;
    private Map<String, Map<String, Long>> people;
}