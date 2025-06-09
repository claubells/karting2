package com.example.report_service.Services;

import com.example.report_service.dto.ReceiptDTO;
import com.example.report_service.dto.ReservationDTO;
import com.example.report_service.dto.ReportResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ReportService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ReportResponseDTO generateSummary() {
        ReservationDTO[] reservas = restTemplate.getForObject(
                "http://localhost:8081/api/v1/reservation/",
                ReservationDTO[].class
        );

        Map<String, Map<String, Long>> porTurnos = new LinkedHashMap<>();
        Map<String, Map<String, Long>> porGrupos = new LinkedHashMap<>();

        for (ReservationDTO r : reservas) {
            String month = r.getDateReservation().substring(5, 7); // ej: "04"
            int turns = r.getTurnsTimeReservation();
            int group = r.getGroupSizeReservation();

            ReceiptDTO[] receipts = restTemplate.getForObject(
                    "http://localhost:8082/api/v1/receipt/by-reservation-id?reservationId=" + r.getIdReservation(),
                    ReceiptDTO[].class
            );

            for (ReceiptDTO rec : receipts) {
                long ingreso = Math.round(rec.getBaseRateReceipt());

                // Por tipo de turno
                porTurnos
                        .computeIfAbsent(String.valueOf(turns), k -> initMonthMap())
                        .merge(month, ingreso, Long::sum);

                // Por grupo
                String rango = mapGroupToRange(group);
                porGrupos
                        .computeIfAbsent(rango, k -> initMonthMap())
                        .merge(month, ingreso, Long::sum);
            }
        }

        return new ReportResponseDTO(porTurnos, porGrupos);
    }

    private Map<String, Long> initMonthMap() {
        Map<String, Long> m = new LinkedHashMap<>();
        for (String mes : List.of("01", "02", "03", "04", "05")) m.put(mes, 0L);
        return m;
    }

    private String mapGroupToRange(int group) {
        if (group <= 2) return "1-2";
        if (group <= 5) return "3-5";
        if (group <= 10) return "6-10";
        return "11-15";
    }
}