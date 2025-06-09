package com.example.loyaltydiscount_service.Repositories;

import com.example.loyaltydiscount_service.Entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByRutClient(String rutClient);

    @Query("SELECT c FROM ClientEntity c WHERE c.rutClient = :rutClient")
    ClientEntity findByRutClientEntity(String rutClient);

    @Query("SELECT c.nameClient " +
            "FROM ClientEntity  c " +
            "WHERE c.rutClient = :rut ")
    Optional<String> findNameByRutClient(String rut);

    @Query("SELECT c.birthdateClient " +
            "FROM ClientEntity c " +
            "WHERE c.rutClient = :rut")
    Optional<LocalDate> findBirthdateByRut(@Param("rut") String rut);

    @Query("SELECT c.idClient " +
            "FROM ClientEntity c " +
            "WHERE c.rutClient = :rut")
    Optional<Long> getIdByRutClient(String rut);


    Optional<ClientEntity> findByIdClient(Long id);
}
