package com.example.reservation_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@FeignClient(value = "reservation-service",
        path = "/api/birthday",
        configuration = {FeignClient.class})
public interface BirthdayFeignClient {

    // Endpoint consumido por FeignClient
    @GetMapping("/discount/{birthdateClient}{reservationDate}{groupSizeReservation}")
    Double GetBirthdayDiscount(@PathVariable LocalDate birthdateClient, @PathVariable LocalDate reservationDate, @PathVariable int groupSizeReservation);
}
