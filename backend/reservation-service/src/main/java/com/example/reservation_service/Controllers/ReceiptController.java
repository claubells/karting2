package com.example.reservation_service.Controllers;

import com.example.reservation_service.Entities.ReceiptEntity;
import com.example.reservation_service.Services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/receipt")
@CrossOrigin("*")
public class ReceiptController {
    @Autowired
    ReceiptService receiptService;


    @PostMapping("/")
    public ResponseEntity<?> createReceipt(@RequestBody ReceiptEntity receipt) {
        try {
            ReceiptEntity newReceipt = receiptService.createReceipt(receipt);
            System.out.println("Creando un nuevo comprobante ***\n ");
            return ResponseEntity.ok( newReceipt);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado a la hora de crear el comprobante");
        }
    }

    @PostMapping("/simulate")
    public ResponseEntity<?> simulateReceipt(@RequestBody ReceiptEntity receipt) {
        try {
            ReceiptEntity simulatedReceipt = receiptService.simulateReceipt(receipt);
            return ResponseEntity.ok(simulatedReceipt);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al simular el recibo");
        }
    }

    @GetMapping("/by-reservation-id") //obtiene todos los receipt segun el id de reserva
    public ResponseEntity<List<ReceiptEntity>> getReceiptsByReservationId(@RequestParam String reservationId) {
        System.out.println("\nObteniendo comprobantes por reserva");
        List<ReceiptEntity> receipts = receiptService.getReceiptsByReservationId(reservationId);
        return ResponseEntity.ok(receipts);
    }

}
