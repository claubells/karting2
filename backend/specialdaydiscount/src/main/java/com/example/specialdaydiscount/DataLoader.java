package com.example.specialdaydiscount;

import com.example.specialdaydiscount.Entities.BirthdayEntity;
import com.example.specialdaydiscount.Entities.HolidayEntity;
import com.example.specialdaydiscount.Repositories.BirthdayRepository;
import com.example.specialdaydiscount.Repositories.HolidayRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final HolidayRepository holidayRepository;

    private final BirthdayRepository birthdayRepository;

    public DataLoader(HolidayRepository holidayRepository, BirthdayRepository birthdayRepository) {
        this.holidayRepository = holidayRepository;
        this.birthdayRepository = birthdayRepository;
    }

    @Override
    public void run(String... args) {
        if (holidayRepository.count() == 0) {
            List<HolidayEntity> feriados = List.of(
                    new HolidayEntity(null, LocalDate.of(1, 1, 1), "Descuento Fines de Semana", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 1, 1), "Año Nuevo", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 4, 18), "Viernes Santo", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 4, 19), "Sábado Santo", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 5, 1), "Día del Trabajo", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 5, 21), "Glorias Navales", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 6, 20), "Día Nacional de los Pueblos Indígenas", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 6, 29), "San Pedro y San Pablo", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 7, 16), "Virgen del Carmen", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 8, 15), "Asunción de la Virgen", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 9, 18), "Independencia Nacional", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 9, 19), "Glorias del Ejército", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 10, 12), "Encuentro de Dos Mundos", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 10, 31), "Iglesias Evangélicas y Protestantes", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 11, 1), "Todos los Santos", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 11, 16), "Elecciones Presidenciales y Parlamentarias", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 12, 8), "Inmaculada Concepción", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 12, 14), "Elección Presidencial (Segunda Vuelta)", 0.21),
                    new HolidayEntity(null, LocalDate.of(2025, 12, 25), "Navidad", 0.21)
            );

            holidayRepository.saveAll(feriados);
            System.out.println("✅ Feriados precargados en la base de datos.");
        }

        if (birthdayRepository.count() == 0) {
            List<BirthdayEntity> cumples = List.of(
                    new BirthdayEntity(null, 1, 2,"Sin descuento de cumpleaños", 0.0),
                    new BirthdayEntity(null, 3, 5, "Descuento de cumpleaños", 0.5),
                    new BirthdayEntity(null, 6, 10, "Descuento de cumplaños", 0.5)
            );
            birthdayRepository.saveAll(cumples);
            System.out.println("✅ Descuentos de cumpleaños precargados en la base de datos.");
        }
    }
}
