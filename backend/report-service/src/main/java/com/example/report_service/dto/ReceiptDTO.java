package com.example.report_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {
    private Long reservationId;
    private double baseRateReceipt;
}