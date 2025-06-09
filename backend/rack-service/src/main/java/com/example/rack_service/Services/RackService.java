package com.example.rack_service.Services;

import com.example.rack_service.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RackService {

    @Autowired
    RestTemplate restTemplate;

    public List<ReservationDTO> getAllReservations() {
        String url = "http://localhost:8096/api/reservation/rack";
        ResponseEntity<ReservationDTO[]> response = restTemplate.getForEntity(url, ReservationDTO[].class);
        return Arrays.asList(response.getBody());
    }
}