package com.example.rack_service.dto;

import lombok.Data;

@Data
public class ReservationDTO {
    private Long idReservation;
    private String dateReservation; // "2025-05-22"
    private String startHourReservation; // "14:00"
    private String finalHourReservation; // "15:00"
    private String holdersReservation;
    private int groupSizeReservation;
    private String statusReservation;
    private int turnsTimeReservation;
}