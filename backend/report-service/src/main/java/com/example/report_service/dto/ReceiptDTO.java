package com.example.report_service.dto;

import lombok.Data;

@Data
public class ReceiptDTO {
    private Long idReceipt;
    private String rutClientReceipt;
    private double baseRateReceipt;
    private double finalAmount;
    private double ivaAmount;
}