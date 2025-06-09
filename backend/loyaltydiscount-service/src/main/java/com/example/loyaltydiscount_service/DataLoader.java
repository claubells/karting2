package com.example.loyaltydiscount_service;

import com.example.loyaltydiscount_service.Entities.LoyaltyDiscountEntity;
import com.example.loyaltydiscount_service.Repositories.LoyaltyDiscountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final LoyaltyDiscountRepository repository;

    public DataLoader(LoyaltyDiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            List<LoyaltyDiscountEntity> discounts = List.of(
                    new LoyaltyDiscountEntity(null, 0, 0), //sin descuento
                    new LoyaltyDiscountEntity(null, 1, 0),
                    new LoyaltyDiscountEntity(null, 2,0.1),// 10% entre 2 a 4 veces
                    new LoyaltyDiscountEntity(null, 3,0.1),
                    new LoyaltyDiscountEntity(null, 4,0.1),
                    new LoyaltyDiscountEntity(null, 5,0.2), // 20% entre 5 y 6 visitas
                    new LoyaltyDiscountEntity(null, 6,0.2),
                    new LoyaltyDiscountEntity(null, 7,0.3) //30% de 7 o más
            );
            repository.saveAll(discounts);
            System.out.println("✅ Descuentos por frecuencia precargados.");
        }
    }
}
