package com.example.report_service.Services;

import com.example.report_service.clients.ReceiptFeignClient;
import com.example.report_service.clients.ReservationFeignClient;
import com.example.report_service.dto.ReceiptDTO;
import com.example.report_service.dto.ReservationDTO;
import com.example.report_service.dto.ReportResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ReportService {

    @Autowired
    ReservationFeignClient reservationFeignClient;

    @Autowired
    ReceiptFeignClient receiptFeignClient;

    public ReportResponseDTO generateReportByTurns() {

        Map<String, Map<String, Integer>> turnsMap = new HashMap<>();

        for (String tipo : List.of("10", "15", "20")) {
            Map<String, Integer> porMes = new HashMap<>();
            for (String mes : List.of("01", "02", "03", "04", "05", "06")) {
                int ingreso = obtenerIngresoPorVueltasYMes(tipo, mes); // ← tu método actual
                porMes.put(mes, ingreso);
            }

            // Calcular total y agregar
            int total = porMes.values().stream().mapToInt(i -> i).sum();
            porMes.put("total", total);

            turnsMap.put(tipo, porMes);
        }

        ReportResponseDTO dto = new ReportResponseDTO();
        dto.setTurns(turnsMap);
        return dto;
    }

    public ReportResponseDTO generateReportByPeople() {

        Map<String, Map<String, Integer>> peopleMap = new HashMap<>();

        for (String rango : List.of("1-2", "3-5", "6-10", "11-15")) {
            Map<String, Integer> porMes = new HashMap<>();
            for (String mes : List.of("01", "02", "03", "04", "05", "06")) {
                int ingreso = obtenerIngresoPorRangoYMes(rango, mes); // ← tu lógica actual
                porMes.put(mes, ingreso);
            }

            // Calcular total
            int total = porMes.values().stream().mapToInt(i -> i).sum();
            porMes.put("total", total);
            peopleMap.put(rango, porMes);
        }

        ReportResponseDTO dto = new ReportResponseDTO();
        dto.setPeople(peopleMap);
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
        return reservationFeignClient.getMinimalReservations();
    }

    private List<ReceiptDTO> fetchReceipts() {
        return receiptFeignClient.getMinimalReceipts();
    }

    public int obtenerIngresoPorVueltasYMes(String tipoStr, String mes) {
        int tipo = Integer.parseInt(tipoStr);
        return receiptFeignClient.getIngresoPorVueltasYMese(tipo, mes);
    }

    public int obtenerIngresoPorRangoYMes(String rango, String mes) {
        String[] partes = rango.split("-");
        int min = Integer.parseInt(partes[0]);
        int max = Integer.parseInt(partes[1]);
        return receiptFeignClient.getIngresoPorGrupoYMese(min, max, mes);
    }

}