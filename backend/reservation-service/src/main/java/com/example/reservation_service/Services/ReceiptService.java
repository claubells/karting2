package com.example.reservation_service.Services;

import com.example.reservation_service.Entities.ReceiptEntity;
import com.example.reservation_service.Entities.ReservationEntity;
import com.example.reservation_service.Repositories.ReceiptRepository;
import com.example.reservation_service.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    /*
    public ReceiptEntity createReceipt(ReceiptEntity receipt) {

        try{
            System.out.println("\nCreando el comprobante *** ");
            System.out.println("Rut del cliente: " + receipt.getRutClientReceipt());
            System.out.println("Reserva ID: " + receipt.getReservationId());
            Long reservationId = receipt.getReservationId();

            ReservationEntity reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new RuntimeException("No se encontró la reserva con ID: " + reservationId));

            ClientEntity client = clientRepository.findByRutClientEntity(receipt.getRutClientReceipt());

            String nameClient = client.getNameClient();
            receipt.setNameClientReceipt(nameClient);

            double baseRate = KartPriceConfig.getPriceByLaps(reservation.getTurnsTimeReservation());
            receipt.setBaseRateReceipt(baseRate);

            double groupSizeDiscount = getGroupSizeDiscount(reservation.getGroupSizeReservation());
            receipt.setGroupSizeDiscount(groupSizeDiscount);

            double birthdayDiscount = getBirthdayDiscount(client.getBirthdateClient(), reservation.getDateReservation());
            receipt.setBirthdayDiscount(birthdayDiscount);

            double loyalityDiscount = getMonthlyFrequencyDiscount(receipt.getRutClientReceipt());
            receipt.setLoyaltyDiscount(loyalityDiscount);

            double specialDaysDiscount = receipt.getSpecialDaysDiscount();
            receipt.setSpecialDaysDiscount(specialDaysDiscount);

            double maxDiscount = getMaxDiscount(
                    receipt.getGroupSizeDiscount(),
                    receipt.getBirthdayDiscount(),
                    receipt.getLoyaltyDiscount(),
                    specialDaysDiscount
            );
            receipt.setMaxDiscount(maxDiscount);

            double finalAmount = baseRate - (baseRate * maxDiscount);
            receipt.setFinalAmount(finalAmount);

            double ivaAmount = finalAmount * KartPriceConfig.IVA;
            receipt.setIvaAmount(ivaAmount);

            double totalAmount = finalAmount + ivaAmount;
            receipt.setTotalAmount(totalAmount);

            // cambiamos el estado de la reserva a pagada:
            reservation.setStatusReservation("pagada");
            // creamos el recibo
            return receiptRepository.save(receipt);
        }
        catch(Exception e){
            System.err.println("Error al crear El comprobante");
            throw e;
        }
    }


    public ReceiptEntity simulateReceipt(ReceiptEntity receipt) {

        try{
            System.out.println("\nCreando el comprobante SIMULADO*** \n");
            Long reservationId = receipt.getReservationId();

            ReservationEntity reservation = reservationRepository.findById(receipt.getReservationId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la reserva con ID: " + receipt.getReservationId()));

            ClientEntity client = clientRepository.findByRutClientEntity(receipt.getRutClientReceipt());
            System.out.println("RUT recibido: " + receipt.getRutClientReceipt());
            System.out.println("Reserva ID recibido: " + receipt.getReservationId());
            System.out.println("Special day discount: " + receipt.getSpecialDaysDiscount());

            String nameClient = client.getNameClient();
            receipt.setNameClientReceipt(nameClient);

            double baseRate = KartPriceConfig.getPriceByLaps(reservation.getTurnsTimeReservation());
            double groupSizeDiscount = getGroupSizeDiscount(reservation.getGroupSizeReservation());
            double birthdayDiscount = getBirthdayDiscount(client.getBirthdateClient(), reservation.getDateReservation());
            double loyaltyDiscount = getMonthlyFrequencyDiscount(receipt.getRutClientReceipt());

            double specialDaysDiscount = receipt.getSpecialDaysDiscount();
            receipt.setSpecialDaysDiscount(specialDaysDiscount);

            double maxDiscount = getMaxDiscount(
                    groupSizeDiscount,
                    birthdayDiscount,
                    loyaltyDiscount,
                    specialDaysDiscount
            );

            double finalAmount = baseRate - (baseRate * maxDiscount);
            double ivaAmount = finalAmount * KartPriceConfig.IVA;
            double totalAmount = finalAmount + ivaAmount;

            // Crear objeto simulación
            ReceiptEntity simulated = new ReceiptEntity();
            simulated.setRutClientReceipt(receipt.getRutClientReceipt());
            simulated.setNameClientReceipt(nameClient);
            simulated.setBaseRateReceipt(baseRate);
            simulated.setGroupSizeDiscount(groupSizeDiscount);
            simulated.setBirthdayDiscount(birthdayDiscount);
            simulated.setLoyaltyDiscount(loyaltyDiscount);
            simulated.setSpecialDaysDiscount(specialDaysDiscount);
            simulated.setMaxDiscount(maxDiscount);
            simulated.setFinalAmount(finalAmount);
            simulated.setIvaAmount(ivaAmount);
            simulated.setTotalAmount(totalAmount);
            simulated.setClientId(client.getIdClient());
            simulated.setReservationId(reservationId);

            return simulated;
        }
        catch(Exception e){
            System.err.println("Error al crear El comprobante");
            throw e;
        }
    }*/

    public List<ReceiptEntity> getReceiptsByReservationId(String reservationId){
        List<ReceiptEntity> reciepts = receiptRepository.findAllByReservationId(reservationId);
        System.out.println("\nRecibos de la reserva con ID: " + reservationId+ "***** \n");
        for(ReceiptEntity receipt : reciepts){
            System.out.println("El comprobante con ID: " + receipt.getIdReceipt() + " pertenece a la reserva con ID: " + receipt.getReservationId());
        }
        return reciepts;
    }

}
