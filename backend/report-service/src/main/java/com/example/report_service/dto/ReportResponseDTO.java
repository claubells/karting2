package com.example.report_service.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDTO {
    private Map<Integer, Map<String, Double>> turns;
    private Map<String, Map<String, Double>> people;
}