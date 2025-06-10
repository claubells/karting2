package com.example.reservation_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "reservation-service",
        path = "/api/people",
        contextId = "reservationToPeopleFeign",
        configuration = {FeignClient.class})
public interface PeopleFeignClient {

    @GetMapping("/discount/{numberPeople}")
    Double getPeopleDiscount(@PathVariable int numberPeople);
}
