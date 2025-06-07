package com.example.loyaltydiscount_service.Repositories;

import com.example.loyaltydiscount_service.Entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByRutClient(String rutClient);

    @Query("SELECT c FROM ClientEntity c WHERE c.rutClient = :rutClient")
    ClientEntity findByRutClientEntity(String rutClient);
}
