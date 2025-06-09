package com.example.reservation_service.Repositories;

import com.example.reservation_service.Entities.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {

    @Modifying
    @Query("DELETE FROM ReceiptEntity r " +
            "WHERE r.reservationId = :reservationId")
    void deleteByReservationId(@Param("reservationId") Long reservationId);

    @Query("SELECT r " +
            "FROM ReceiptEntity r " +
            "WHERE r.reservationId = :idReservation")
    List<ReceiptEntity> findAllByReservationId(String idReservation);


    @Query("""
    SELECT COUNT(r)
    FROM ReceiptEntity r
    JOIN ReservationEntity re ON r.reservationId = re.idReservation
    WHERE r.rutClientReceipt = :rut
      AND EXTRACT(YEAR FROM re.dateReservation) = :year
      AND EXTRACT(MONTH FROM re.dateReservation) = :month""")
    int countReceiptsByRutAndMonth(@Param("rut") String rut, @Param("year") int year, @Param("month") int month);
}
