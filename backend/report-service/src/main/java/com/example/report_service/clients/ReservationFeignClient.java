package com.example.report_service.clients;

import com.example.report_service.dto.ReservationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "report-service",
        path = "/api/reservation",
        contextId = "reportToReservationFeign",
        configuration = {FeignClient.class})
public interface ReservationFeignClient {

    @GetMapping("/minimal")
    List<ReservationDTO> getMinimalReservations();

}
