package com.example.reservation_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@FeignClient(value = "reservation-service",
        path = "/api/client",
        contextId = "reservationToClientFeign",
        configuration = {FeignClient.class})
public interface ClientFeignClient {

    @GetMapping("/getname/{rut}")
    String getNameClientByRut(@PathVariable String rut);

    @GetMapping("/getemail/{rut}")
    String getEmailClientByRut(@PathVariable String rut);

    @GetMapping("/getbirthdate/{rut}")
    LocalDate getBirthdateClientByRut(@PathVariable String rut);

    @GetMapping("/getid/{rut}")
    Long getIdClientByRut(@PathVariable String rut);

}
