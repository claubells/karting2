package com.example.report_service.dto;

import lombok.Data;

@Data
public class ReservationDTO {
    private Long idReservation;
    private String holdersReservation;
    private String dateReservation;        // formato "2025-04-10"
    private int turnsTimeReservation;      // 10, 15, 20
    private int groupSizeReservation;      // 1..n
}