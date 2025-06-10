package com.example.reservation_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@FeignClient(value = "reservation-service",
        path = "/api/loyalty",
        configuration = {FeignClient.class})
public interface LoyaltyFeignClient {

    @GetMapping("/{rut}/{date}")
    Double getLoyaltyDiscount(@PathVariable String rut, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

}
