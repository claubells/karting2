package com.example.loyaltydiscount_service.Services;

import com.example.loyaltydiscount_service.Entities.ClientEntity;
import com.example.loyaltydiscount_service.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public ArrayList<ClientEntity> getClients(){
        return (ArrayList<ClientEntity>) clientRepository.findAll();
    }

    public ClientEntity createClient(ClientEntity client) {
        try {
            if(clientRepository.findByRutClient(client.getRutClient()).isPresent()){
                throw new RuntimeException("\nEl RUT: "+client.getRutClient()+" ya está registrado.");
            }
            System.out.println("Cliente guardado en la base de datos...");
            return clientRepository.save(client);
        } catch (Exception e) {
            System.err.println("Error al guardar el cliente: " + e.getMessage());
            throw e;
        }
    }

    public Optional<ClientEntity> findByRut(String rut) {
        return clientRepository.findByRutClient(rut);
    }

    public Optional<String> findNameByRut(String rut) {
        return clientRepository.findNameByRutClient(rut);
    }

    public Optional<LocalDate> findBirthdateByRut(String rut) {
        return clientRepository.findBirthdateByRut(rut);
    }

    public Optional<String> findEmailByRut(String rut) {
        return clientRepository.findEmailByRutClient(rut);
    }

    public Optional<Long> findIdByRut(String rut) {
        return clientRepository.getIdByRutClient(rut);
    }

    public Optional<ClientEntity> findById(Long id) {
        return clientRepository.findByIdClient(id);
    }
}
