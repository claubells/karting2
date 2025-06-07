package com.example.reservation_service.Repositories;

import com.example.reservation_service.Entities.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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


}
