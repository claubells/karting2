package com.example.loyaltydiscount_service.Services;

import com.example.loyaltydiscount_service.Repositories.LoyaltyDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class LoyaltyDiscountService {

    @Autowired
    LoyaltyDiscountRepository loyaltyDiscountServiceRepository;

    @Autowired
    private RestTemplate restTemplate;

    public double getMonthlyFrequencyDiscount(String rut, LocalDate date) {
        try {
            String url = "http://localhost:8096/api/receipt/monthly-count-frequency/" + rut + "/" + date;
            int cantidad = restTemplate.getForObject(url, Integer.class);
            System.out.println("Cantidad de visitas este mes: "+cantidad);

            if (cantidad >= 0) {
                if (cantidad >= 7) {
                    Optional<Double> descuento = loyaltyDiscountServiceRepository.getDiscountByVisit(7);
                    double valor = descuento.orElse(0.0);
                    System.out.println("Descuento aplicado (≥7 visitas): " + valor);
                    return valor;
                }
                Optional<Double> descuento = loyaltyDiscountServiceRepository.getDiscountByVisit(cantidad);
                double valor = descuento.orElse(0.0);
                System.out.println("Descuento aplicado (" + cantidad + " visitas): " + valor);
                return valor;
            }
            System.out.println("Error al calcular la cantidad de visitas.");
            return 0.0;
        } catch (Exception e) {
            System.err.println("Error al llamar a reservation-service: " + e.getMessage());
            return 0.0;
        }
    }
}
