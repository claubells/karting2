package com.example.specialdaydiscount.Entities;

import java.time.LocalDate;
import java.util.Set;

public class HolidayConfig {

    public static final Set<LocalDate> HOLIDAYS = Set.of(
            LocalDate.of(2025, 4, 18), // Viernes Santo
            LocalDate.of(2025, 4, 19), // Sábado Santo
            LocalDate.of(2025, 5, 1),  // Día del Trabajo
            LocalDate.of(2025, 5, 21), // Glorias Navales
            LocalDate.of(2025, 6, 20)  // Día Nacional de los Pueblos Indígenas
    );
}
