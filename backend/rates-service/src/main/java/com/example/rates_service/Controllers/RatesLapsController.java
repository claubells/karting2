package com.example.rates_service.Controllers;

import com.example.rates_service.Services.RatesLapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rates")
@CrossOrigin("*")
public class RatesLapsController {

    @Autowired
    RatesLapsService ratesLapsService;

    // Endpoint consumido por FeignClient
    @GetMapping("/laps/{laps}")
    public ResponseEntity<Double> getRatesByLaps(@PathVariable int laps) {
        Double rate = ratesLapsService.getRateByLaps(laps);
        return ResponseEntity.ok(rate);
    }
}
