package com.example.reservation_service.Controllers;

import com.example.reservation_service.Entities.ReceiptEntity;
import com.example.reservation_service.Services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/receipt")
@CrossOrigin("*")
public class ReceiptController {
    @Autowired
    ReceiptService receiptService;


    @PostMapping("/")
    public ResponseEntity<?> createReceipt(@RequestBody ReceiptEntity receipt) {
        try {
            System.out.println("\ncreate recipt feik");

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

            System.out.println("\ncreate recipt simulation feik");
            System.out.println("➡️ Recibo simulado recibido:");
            System.out.println("RUT: " + receipt.getRutClientReceipt());
            System.out.println("Reserva ID: " + receipt.getReservationId());
            System.out.println("Cliente ID: " + receipt.getClientId());
            System.out.println("Descuento: " + receipt.getSpecialDaysDiscount());

            ReceiptEntity simulatedReceipt = receiptService.simulateReceipt(receipt);
            return ResponseEntity.ok(simulatedReceipt);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al simular el recibo");
        }
    }

    @GetMapping("/by-reservation-id") //obtiene todos los receipt según el id de reserva
    public ResponseEntity<List<ReceiptEntity>> getReceiptsByReservationId(@RequestParam String reservationId) {
        System.out.println("\nObteniendo comprobantes por reserva");
        List<ReceiptEntity> receipts = receiptService.getReceiptsByReservationId(reservationId);
        return ResponseEntity.ok(receipts);
    }

    @GetMapping("/monthly-count-frequency/{rut}/{date}") //obtiene el número de recipt de ese mes by rut
    public ResponseEntity<Integer> getMonthlyFrequency(@PathVariable String rut,  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        System.out.println("\nObteniendo el numero de frecuencias de el mes de reserva por el rut " + rut);
        int receipts = receiptService.getMonthlyFrequency(rut, date);
        return ResponseEntity.ok(receipts);
    }

}
