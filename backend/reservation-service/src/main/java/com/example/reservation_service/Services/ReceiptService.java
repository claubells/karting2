package com.example.reservation_service.Services;

import com.example.reservation_service.Entities.ReceiptEntity;
import com.example.reservation_service.Entities.ReservationEntity;
import com.example.reservation_service.Repositories.ReceiptRepository;
import com.example.reservation_service.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestTemplate restTemplate;

    public ReceiptEntity createReceipt(ReceiptEntity receipt) {

        try{
            System.out.println("\nCreando el comprobante *** ");
            System.out.println("Rut del cliente: " + receipt.getRutClientReceipt());
            System.out.println("Reserva ID: " + receipt.getReservationId());
            Long reservationId = receipt.getReservationId();

            ReservationEntity reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new RuntimeException("No se encontró la reserva con ID: " + reservationId));

            // GET name by rut cliente
            String url1 = "http://localhost:8091/api/client/getname/" + receipt.getRutClientReceipt();

            String nameClient = restTemplate.getForObject(url1, String.class);

            if (nameClient.isEmpty()) {
                System.out.println("El nombre de cliente no existe.");
                return null;
            }

            //seteamos el nombre del cliente en el Receipt
            receipt.setNameClientReceipt(nameClient);

            // GET baseRate by laps
            String url2 = "http://localhost:8094/api/rates/laps/" + reservation.getTurnsTimeReservation();
            Double baseRate = restTemplate.getForObject(url2, Double.class);
            if(baseRate == null){
                System.out.println("El precio de laps no existe.");
                return null;
            }
            receipt.setBaseRateReceipt(baseRate);


            double groupSizeDiscount = getGroupSizeDiscount(reservation.getGroupSizeReservation());
            receipt.setGroupSizeDiscount(groupSizeDiscount);

            double birthdayDiscount = getBirthdayDiscount(receipt.getRutClientReceipt(), reservation.getDateReservation(), reservation.getGroupSizeReservation());
            receipt.setBirthdayDiscount(birthdayDiscount);

            double loyalityDiscount = getFrequencyDiscount(receipt.getRutClientReceipt(), reservation.getDateReservation());
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

            double ivaAmount = finalAmount * 0.19;
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

    private double getGroupSizeDiscount(int groupSizeReservation) {
        String url = "http://localhost:8092/api/people/discount/" + groupSizeReservation;
        Double baseRate = restTemplate.getForObject(url, Double.class);
        if(baseRate == null){
            System.out.println("El precio de laps no existe.");
            return 0.0;
        }
        return baseRate;
    }

    public double getBirthdayDiscount(String rutClient, LocalDate reservationDate, int groupSizeReservation) {
        String url1 = "http://localhost:8091/api/client/getbirthdate/" + rutClient;

        LocalDate birthdateClient = restTemplate.getForObject(url1, LocalDate.class);

        if (birthdateClient.getDayOfMonth() == reservationDate.getDayOfMonth() && birthdateClient.getMonthValue() == reservationDate.getMonthValue()) {
            String url = "http://localhost:8097/api/birthday/discount/" + birthdateClient + reservationDate + groupSizeReservation;
            Double discount = restTemplate.getForObject(url, Double.class);

            if(discount == null){
                System.out.println("El descuento no se pudo calcular.");
                return 0.0;
            }
            return discount;
        }
        return 0.0;
    }


    public ReceiptEntity simulateReceipt(ReceiptEntity receipt) {

        try{
            System.out.println("\nCreando el comprobante SIMULADO*** \n");
            Long reservationId = receipt.getReservationId();

            ReservationEntity reservation = reservationRepository.findById(receipt.getReservationId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la reserva con ID: " + receipt.getReservationId()));

            // GET name by rut client
            String url1 = "http://localhost:8091/api/client/getname/" + receipt.getRutClientReceipt();

            String nameClient = restTemplate.getForObject(url1, String.class);

            if (nameClient.isEmpty()) {
                System.out.println("El nombre de cliente no existe.");
                return null;
            }

            System.out.println("RUT recibido: " + receipt.getRutClientReceipt());
            System.out.println("Reserva ID recibido: " + receipt.getReservationId());
            System.out.println("Special day discount: " + receipt.getSpecialDaysDiscount());

            // GET baseRate by laps
            String url2 = "http://localhost:8094/api/rates/laps/" + reservation.getTurnsTimeReservation();
            Double baseRate = restTemplate.getForObject(url2, Double.class);
            if(baseRate == null){
                System.out.println("El precio de laps no existe.");
                return null;
            }

            double groupSizeDiscount = getGroupSizeDiscount(reservation.getGroupSizeReservation());
            double birthdayDiscount = getBirthdayDiscount(receipt.getRutClientReceipt(), reservation.getDateReservation(), reservation.getGroupSizeReservation());
            double loyaltyDiscount = getFrequencyDiscount(receipt.getRutClientReceipt(), reservation.getDateReservation());

            double specialDaysDiscount = receipt.getSpecialDaysDiscount();
            receipt.setSpecialDaysDiscount(specialDaysDiscount);

            double maxDiscount = getMaxDiscount(
                    groupSizeDiscount,
                    birthdayDiscount,
                    loyaltyDiscount,
                    specialDaysDiscount
            );

            double finalAmount = baseRate - (baseRate * maxDiscount);
            double ivaAmount = finalAmount * 0.19;
            double totalAmount = finalAmount + ivaAmount;

            Long clientId = getClientId(receipt.getRutClientReceipt());

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
            simulated.setClientId(clientId);
            simulated.setReservationId(reservationId);

            return simulated;
        }
        catch(Exception e){
            System.err.println("Error al crear El comprobante");
            e.printStackTrace();
            throw e;
        }
    }

    public List<ReceiptEntity> getReceiptsByReservationId(String reservationId){
        List<ReceiptEntity> reciepts = receiptRepository.findAllByReservationId(reservationId);
        System.out.println("\nRecibos de la reserva con ID: " + reservationId+ "***** \n");
        for(ReceiptEntity receipt : reciepts){
            System.out.println("El comprobante con ID: " + receipt.getIdReceipt() + " pertenece a la reserva con ID: " + receipt.getReservationId());
        }
        return reciepts;
    }

    public Long getClientId(String rut){
        String url = "http://localhost:8091/api/client/getid/" + rut;
        Long clientId = restTemplate.getForObject(url, Long.class);
        if(clientId == null){
            System.out.println("El id del cliente no existe rut: " + rut);
            return null;
        }
        return clientId;
    }

    public Double getFrequencyDiscount(String rut, LocalDate date) {
        String url = "http://localhost:8091/api/loyalty/" + rut + "/" + date;
        Double descuento = restTemplate.getForObject(url, Double.class);
        if(descuento == null){
            System.out.println("No se pudo recuperar el descuento");
            return 0.0;
        }
        return descuento;
    }

    public int getMonthlyFrequency(String rut, LocalDate date) {
        return receiptRepository.countReceiptsByRutAndMonth(rut, date.getYear(), date.getMonthValue());
    }

    private double getMaxDiscount(double groupSizeDiscount, double birthdayDiscount, double loyaltyDiscount, double specialDaysDiscount) {
        return Math.max(
                Math.max(groupSizeDiscount, birthdayDiscount),
                Math.max(loyaltyDiscount, specialDaysDiscount)
        );
    }

}
