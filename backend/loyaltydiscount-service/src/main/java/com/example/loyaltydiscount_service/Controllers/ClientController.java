package com.example.loyaltydiscount_service.Controllers;

import com.example.loyaltydiscount_service.Entities.ClientEntity;
import com.example.loyaltydiscount_service.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loyalty-service/client")
@CrossOrigin("*")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/") //obtiene todos los clientes
    public ResponseEntity<List<ClientEntity>> listAllClients() {
        List<ClientEntity> client = clientService.getClients();
        return ResponseEntity.ok(client);
    }

    @PostMapping("/")
    public ResponseEntity<?> createClient(@RequestBody ClientEntity client) {
        try {
            ClientEntity newClient = clientService.createClient(client);
            System.out.println("CLIENTE YA CREADO ***\nID: "+newClient.getIdClient()+"\nRUT: "+ newClient.getRutClient()+"\nNAME: "+newClient.getNameClient()+"\nBIRTHDATE: "+newClient.getBirthdateClient()+"\nEMAIL: "+newClient.getEmailClient());
            return ResponseEntity.ok(newClient);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{rut}")
    public ResponseEntity<?> getClientByRut(@PathVariable String rut) {
        Optional<ClientEntity> client = clientService.findByRut(rut);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente no encontrado");
        }
    }

    /*
    @GetMapping("/discount-frequencies")
    public ResponseEntity<Double> getClientlLoyaltyDiscount(@RequestParam String rut) {
        try {
            // se hace una solicitud http de todos los receipt de ese rut
            // double all_receipt_by_rut =
            return ResponseEntity.ok(discount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0.0);
        }
    }*/

}
