package com.example.rates_service;

import com.example.rates_service.Entities.KartEntity;
import com.example.rates_service.Entities.RatesLapsEntity;
import com.example.rates_service.Repositories.KartRepository;
import com.example.rates_service.Repositories.RatesLapsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RatesLapsRepository ratesLapsRepository;

    private final KartRepository kartRepository;

    public DataLoader(RatesLapsRepository ratesLapsRepository, KartRepository kartRepository) {
        this.ratesLapsRepository = ratesLapsRepository;
        this.kartRepository = kartRepository;
    }

    @Override
    public void run(String... args) {
        if (ratesLapsRepository.count() == 0) {
            List<RatesLapsEntity> lapsRates = List.of(
                    new RatesLapsEntity(null, 10, 15000),
                    new RatesLapsEntity(null, 15, 20000),
                    new RatesLapsEntity(null, 20, 25000)
            );

            ratesLapsRepository.saveAll(lapsRates);
            System.out.println("✅ Tarifas por cantidad de vueltas precargadas.");
        }

        // Cargar karts
        if (kartRepository.count() == 0) {
            List<KartEntity> karts = List.of(
                    new KartEntity(null, "K001", "Sodikart RT8", "available"),
                    new KartEntity(null, "K002", "Sodikart RT8", "available"),
                    new KartEntity(null, "K003", "Sodikart RT8", "available"),
                    new KartEntity(null, "K004", "Sodikart RT8", "available"),
                    new KartEntity(null, "K005", "Sodikart RT8", "available"),
                    new KartEntity(null, "K006", "Sodikart RT8", "available"),
                    new KartEntity(null, "K007", "Sodikart RT8", "available"),
                    new KartEntity(null, "K008", "Sodikart RT8", "available"),
                    new KartEntity(null, "K009", "Sodikart RT8", "available"),
                    new KartEntity(null, "K010", "Sodikart RT8", "available"),
                    new KartEntity(null, "K011", "Sodikart RT8", "available"),
                    new KartEntity(null, "K012", "Sodikart RT8", "available"),
                    new KartEntity(null, "K013", "Sodikart RT8", "available"),
                    new KartEntity(null, "K014", "Sodikart RT8", "available"),
                    new KartEntity(null, "K015", "Sodikart RT8", "available")
            );
            kartRepository.saveAll(karts);
            System.out.println("✅ Karts precargados.");
        }

    }
}
