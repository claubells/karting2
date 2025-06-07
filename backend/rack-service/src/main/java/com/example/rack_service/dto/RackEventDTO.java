package com.example.rack_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//respuesta del frontend
@Data
@AllArgsConstructor
public class RackEventDTO {
    private String id;
    private String title;      // "Titular: Juan"
    private String start;      // "2025-05-22T14:00:00"
    private String end;        // "2025-05-22T15:00:00"
    private String color;      // opcional: "#1976d2"
}