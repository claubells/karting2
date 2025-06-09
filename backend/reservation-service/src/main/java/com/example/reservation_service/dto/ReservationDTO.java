package com.example.reservation_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long idReservation;
    private LocalDate dateReservation;        // formato "2025-04-10"
    private int turnsTimeReservation;      // 10, 15, 20
    private int groupSizeReservation;      // 1..n
}