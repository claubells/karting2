package com.example.peoplediscount_service;

import com.example.peoplediscount_service.Entities.PeopleDiscountEntity;
import com.example.peoplediscount_service.Repositories.PeopleDiscountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final PeopleDiscountRepository repository;

    public DataLoader(PeopleDiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            List<PeopleDiscountEntity> discounts = List.of(
                    new PeopleDiscountEntity(null, 1, 2, 0.0),
                    new PeopleDiscountEntity(null, 3, 5, 0.10),
                    new PeopleDiscountEntity(null, 6, 10, 0.20),
                    new PeopleDiscountEntity(null, 11,15,0.30)
            );
            repository.saveAll(discounts);
            System.out.println("✅ Descuentos por cantidad de personas precargados.");
        }
    }
}
