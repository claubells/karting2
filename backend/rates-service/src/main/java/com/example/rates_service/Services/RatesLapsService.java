package com.example.rates_service.Services;

import com.example.rates_service.Repositories.RatesLapsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatesLapsService {
    @Autowired
    RatesLapsRepository ratesLapsRepository;

    public Double getRateByLaps(int laps) {
        return ratesLapsRepository.getRatesLapsEntityByLaps(laps);
    }
}
