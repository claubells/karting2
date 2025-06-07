package com.example.rates_service.Entities;

public class RatesConfig {
    // Precios base por tipo de turno
    public static final double RATE_10_LAPS = 15000;
    public static final double RATE_15_LAPS = 20000;
    public static final double RATE_20_LAPS = 25000;

    public static double getPriceByLaps(int turns) {
        return switch (turns) {
            case 10 -> RATE_10_LAPS;
            case 15 -> RATE_15_LAPS;
            case 20 -> RATE_20_LAPS;
            default -> throw new IllegalArgumentException("Cantidad de vueltas inválida: " + turns);
        };
    }
}
