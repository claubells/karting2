package com.example.rates_service.Services;

import com.example.rates_service.Entities.KartEntity;
import com.example.rates_service.Repositories.KartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KartService {
    @Autowired
    KartRepository kartRepository;

    public List<KartEntity> getKarts(){
        return kartRepository.findAll();
    }

    public List<KartEntity> getAvailableKarts() {
        return kartRepository.findAvailableKarts();
    }
}