package com.example.report_service.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDTO {
    private Map<String, Map<String, Integer>> turns;
    private Map<String, Map<String, Integer>> people;
}