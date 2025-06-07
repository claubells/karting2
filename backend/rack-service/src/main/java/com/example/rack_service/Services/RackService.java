package com.example.rack_service.Services;

import com.example.rack_service.dto.RackEventDTO;
import com.example.rack_service.dto.ReservationDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RackService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<RackEventDTO> getWeekRack(LocalDate startDate, LocalDate endDate) {
        String url = String.format(
                "http://reservation-service/api/v1/reservation/by-range?from=%s&to=%s",
                startDate.toString(), endDate.toString()
        );

        ReservationDTO[] reservas = restTemplate.getForObject(url, ReservationDTO[].class);

        List<RackEventDTO> eventos = new ArrayList<>();

        for (ReservationDTO r : reservas) {
            String start = r.getDateReservation() + "T" + r.getStartHourReservation();
            String end = r.getDateReservation() + "T" + r.getFinalHourReservation();

            RackEventDTO event = new RackEventDTO(
                    String.valueOf(r.getIdReservation()),
                    "Titular: " + r.getHoldersReservation(),
                    start,
                    end,
                    "#1976d2" // Azul MUI por defecto
            );

            eventos.add(event);
        }

        return eventos;
    }
}