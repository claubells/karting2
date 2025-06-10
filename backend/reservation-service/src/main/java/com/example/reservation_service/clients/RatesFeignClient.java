package com.example.reservation_service.clients;

import com.example.reservation_service.dto.KartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "reservation-service",
        path = "/api/client",
        configuration = {FeignClient.class})
public interface RatesFeignClient {

    @GetMapping("/laps/{laps}")
    Double getRatesByLaps(@PathVariable int laps);

    @GetMapping("/available/")
    List<KartDTO> findAvailableKarts();
}
