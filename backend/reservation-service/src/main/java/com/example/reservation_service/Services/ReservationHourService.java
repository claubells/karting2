package com.example.reservation_service.Services;

import com.example.reservation_service.Entities.ReservationHourEntity;
import com.example.reservation_service.Repositories.ReservationHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReservationHourService {

    @Autowired
    private ReservationHourRepository reservationHourRepository;

    public Map<String, Map<String, String>> getHoursReservations() {
        ReservationHourEntity hoursReservation = reservationHourRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        if (hoursReservation == null) return Map.of();

        Map<String, String> weekly = Map.of(
                "min", hoursReservation.getWeeklyHourMin().toString(),
                "max", hoursReservation.getWeeklyHourMax().toString()
        );

        Map<String, String> special = Map.of(
                "min", hoursReservation.getSpecialHourMin().toString(),
                "max", hoursReservation.getSpecialHourMax().toString()
        );

        Map<String, Map<String, String>> response = new HashMap<>();
        response.put("weeklyHours", weekly);
        response.put("specialHours", special);

        return response;
    }
}
