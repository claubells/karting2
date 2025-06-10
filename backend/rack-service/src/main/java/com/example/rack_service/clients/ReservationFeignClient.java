package com.example.rack_service.clients;

import com.example.rack_service.dto.ReservationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "rack-service",
        path = "/api/reservation",
        contextId = "rackToReservationFeign",
        configuration = {FeignClient.class})
public interface ReservationFeignClient {

    @GetMapping("/rack")
    List<ReservationDTO> getAllForRack();

}
