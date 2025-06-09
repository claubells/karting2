package com.example.rack_service.Controllers;

import com.example.rack_service.Services.RackService;
import com.example.rack_service.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rack")
@CrossOrigin("*")
public class RackController {

    @Autowired
    RackService rackService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getRackReservations() {
        return ResponseEntity.ok(rackService.getAllReservations());
    }

}

