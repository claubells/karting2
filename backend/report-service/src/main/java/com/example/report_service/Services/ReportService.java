package com.example.report_service.Services;

import com.example.report_service.dto.ReceiptDTO;
import com.example.report_service.dto.ReservationDTO;
import com.example.report_service.dto.ReportResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ReportService {

    @Autowired
    RestTemplate restTemplate;
    private final List<String> months = List.of("01", "02", "03", "04", "05", "06");

    public ReportResponseDTO generateReportByTurns() {
        List<ReservationDTO> reservations = fetchReservations();
        List<ReceiptDTO> receipts = fetchReceipts();

        Map<Integer, Map<String, Double>> turnsData = new HashMap<>();

        for (int laps : List.of(10, 15, 20)) {
            Map<String, Double> monthMap = new HashMap<>();
            for (String m : months) {
                monthMap.put(m, 0.0);
            }
            turnsData.put(laps, monthMap);
        }

        for (ReservationDTO reservation : reservations) {
            String mes = String.format("%02d", reservation.getDateReservation().getMonthValue());
            int turns = reservation.getTurnsTimeReservation();

            if (!months.contains(mes) || !turnsData.containsKey(turns)) continue;

            for (ReceiptDTO receipt : receipts) {
                if (Objects.equals(receipt.getReservationId(), reservation.getIdReservation())) {
                    double current = turnsData.get(turns).get(mes);
                    turnsData.get(turns).put(mes, current + receipt.getBaseRateReceipt());
                }
            }
        }

        ReportResponseDTO dto = new ReportResponseDTO();
        dto.setTurns(turnsData);
        return dto;
    }

    public ReportResponseDTO generateReportByPeople() {
        List<ReservationDTO> reservations = fetchReservations();
        List<ReceiptDTO> receipts = fetchReceipts();

        Map<String, Map<String, Double>> peopleData = new HashMap<>();
        List<String> ranges = List.of("1-2", "3-5", "6-10", "11-15");

        for (String range : ranges) {
            Map<String, Double> monthMap = new HashMap<>();
            for (String m : months) {
                monthMap.put(m, 0.0);
            }
            peopleData.put(range, monthMap);
        }

        for (ReservationDTO reservation : reservations) {
            String mes = String.format("%02d", reservation.getDateReservation().getMonthValue());
            int group = reservation.getGroupSizeReservation();

            String range = getRange(group);
            if (range == null || !months.contains(mes)) continue;

            for (ReceiptDTO receipt : receipts) {
                if (Objects.equals(receipt.getReservationId(), reservation.getIdReservation())) {
                    double current = peopleData.get(range).get(mes);
                    peopleData.get(range).put(mes, current + receipt.getBaseRateReceipt());
                }
            }
        }

        ReportResponseDTO dto = new ReportResponseDTO();
        dto.setPeople(peopleData);
        return dto;
    }

    private String getRange(int groupSize) {
        if (groupSize >= 1 && groupSize <= 2) return "1-2";
        if (groupSize >= 3 && groupSize <= 5) return "3-5";
        if (groupSize >= 6 && groupSize <= 10) return "6-10";
        if (groupSize >= 11 && groupSize <= 15) return "11-15";
        return null;
    }

    private List<ReservationDTO> fetchReservations() {
        String url = "http://localhost:8096/api/reservation/minimal";
        ResponseEntity<ReservationDTO[]> response = restTemplate.getForEntity(url, ReservationDTO[].class);
        return Arrays.asList(response.getBody());
    }

    private List<ReceiptDTO> fetchReceipts() {
        String url = "http://localhost:8096/api/receipt/minimal";
        ResponseEntity<ReceiptDTO[]> response = restTemplate.getForEntity(url, ReceiptDTO[].class);
        return Arrays.asList(response.getBody());
    }


}