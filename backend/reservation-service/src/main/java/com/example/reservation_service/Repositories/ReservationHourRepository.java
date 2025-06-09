package com.example.reservation_service.Repositories;

import com.example.reservation_service.Entities.ReservationHourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationHourRepository extends JpaRepository<ReservationHourEntity, Long> {
}
