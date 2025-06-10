package com.example.rack_service.Services;

import com.example.rack_service.clients.ReservationFeignClient;
import com.example.rack_service.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RackService {

    @Autowired
    ReservationFeignClient reservationFeignClient;

    public List<ReservationDTO> getAllReservations() {
        return reservationFeignClient.getAllForRack();
    }
}