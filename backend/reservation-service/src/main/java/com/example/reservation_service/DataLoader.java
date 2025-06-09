package com.example.reservation_service;

import com.example.reservation_service.Entities.ReservationHourEntity;
import com.example.reservation_service.Repositories.ReservationHourRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final ReservationHourRepository reservationHourRepository;

    public DataLoader(ReservationHourRepository configRepository) {
        this.reservationHourRepository = configRepository;
    }

    @Override
    public void run(String... args) {
        if (reservationHourRepository.count() == 0) {
            ReservationHourEntity config = new ReservationHourEntity(
                    null,
                    LocalTime.of(14, 0),  // weeklyHourMin
                    LocalTime.of(22, 0),  // weeklyHourMax
                    LocalTime.of(10, 0),  // specialHourMin
                    LocalTime.of(22, 0)   // specialHourMax
            );

            reservationHourRepository.save(config);
            System.out.println("✅ Configuración horaria precargada.");
        }
    }
}
