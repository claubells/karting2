package com.example.reservation_service.Controllers;

import com.example.reservation_service.Entities.ReservationEntity;
import com.example.reservation_service.Repositories.ReservationHourRepository;
import com.example.reservation_service.Services.ReservationHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@RequestMapping("/api/hours")
@CrossOrigin("*")
public class ReservationHourController {
    @Autowired
    private ReservationHourService reservationHourService;

    @GetMapping("/") //obtiene todas las horas de atención
    public ResponseEntity<Map<String, Map<String, String>>> getHours() {
        return ResponseEntity.ok(reservationHourService.getHoursReservations());
    }
}
