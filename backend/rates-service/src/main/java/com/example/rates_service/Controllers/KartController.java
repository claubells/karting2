package com.example.rates_service.Controllers;

import com.example.rates_service.Entities.KartEntity;
import com.example.rates_service.Services.KartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kart")
@CrossOrigin("*")
public class KartController {
    @Autowired
    KartService kartService;

    @GetMapping("/") //obtiene los karts
    public ResponseEntity<List<KartEntity>> listAllKarts() {
        List<KartEntity> kart = kartService.getKarts();
        return ResponseEntity.ok(kart);
    }

    // Endpoint consumido por FeignClient
    @GetMapping("/available/")
    public ResponseEntity<List<KartEntity>> findAvailableKarts() {
        List<KartEntity> kart = kartService.getAvailableKarts();
        return ResponseEntity.ok(kart);
    }
}
