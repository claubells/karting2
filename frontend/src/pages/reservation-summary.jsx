import React, { useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Box, Typography, TextField } from '@mui/material';
import { getReservationById } from '../services/reservation.service';
import { createReceipt, simulateReceipt, getReceiptsByReservationId } from '../services/receipt.service';

export default function ReservationSummary() {

    const navigate = useNavigate();

    const [reservationData, setReservationData] = useState(null);
    const [clientList, setClientList] = useState([]);
    const [receipts, setReceipts] = useState([]);
    const [specialDaysDiscount, setSpecialDaysDiscount] = useState(0.21);
    const [simulatedReceipts, setSimulatedReceipts] = useState([]);


    // 1 primero cargamos los datos de la reserva desde el localStorage
    useEffect(() => {
        const loadInitialData = async () => {
            const idReservation = localStorage.getItem('idReservation');
            if (!idReservation) {
                alert('No hay ID de reserva');
                navigate('/home');
                return;
            }
            // obtenemos la reserva desde el back
            const reservation = await getReservationById(idReservation);
            if (!reservation) {
                alert('No se encontró la reserva');
                navigate('/');
                return;
            }
            setReservationData(reservation);
            setClientList(reservation.clientList || []);
        
        };
        loadInitialData();
    }, []);

    // se carga el descuento guardado si existe
    useEffect(() => {
        const saved = localStorage.getItem('specialDaysDiscount');
        if (saved) setSpecialDaysDiscount(parseFloat(saved));
    }, []);

    // se guarda el descuento en el localStorage cada vez que cambia
    useEffect(() => {
        localStorage.setItem('specialDaysDiscount', specialDaysDiscount.toString());
    }, [specialDaysDiscount]);

    // se simulan los recibos cada vez que cambia la reserva o la lista de clientes
    useEffect(() => {
        if (reservationData && clientList.length > 0) {
            simulateAllReceipts();
        }
    }, [reservationData, clientList]);

    const simulateAllReceipts = async () => {
        try {
            if (!reservationData || clientList.length === 0 || specialDaysDiscount === null) return;

            const simulations = await Promise.all(clientList.map(async (client) => {
                const simulated = await simulateReceipt({
                    rutClientReceipt: client.rutClient,
                    reservationId: reservationData.idReservation,
                    clientId: client.idClient,
                    specialDaysDiscount: specialDaysDiscount,
                });
                return simulated;
            }));

            setSimulatedReceipts(simulations);
        } catch (error) {
            console.error('Error simulando los recibos:', error);
        }
    };

    function isSpecialDay(date) {
        const day = new Date(date).getDay(); // 0 = domingo, 6 = sábado
        return day === 0 || day === 6 || isHoliday(date);
    }
    
    function isHoliday(date) {
        const feriados = [
            '2025-04-18', '2025-04-19', '2025-05-01', '2025-05-21',
        ];
        const dateStr = new Date(date).toISOString().split('T')[0];
        return feriados.includes(dateStr);
    }

    // 3 aquí se crea la reserva
    async function handleSubmitReservation(){
        try {

            if (!reservationData || clientList.length === 0) {
                alert('Datos de reserva inválidos.');
                return;
            }
            // se crean los comprobantes para cada cliente x el back
            for (const client of clientList) {
                await createReceipt({
                    rutClientReceipt: client.rutClient,
                    reservationId: reservationData.idReservation,
                    clientId: client.idClient,
                    specialDaysDiscount: specialDaysDiscount,
                });
            }

            const receiptsData = await getReceiptsByReservationId(reservationData.idReservation);
            setReceipts(receiptsData);

            localStorage.removeItem('specialDaysDiscountSet');
            alert('Reserva y comprobantes creados correctamente.');
            navigate('/home');
        } catch (error) {
            console.error('Error al confirmar la reserva: ', error);
            alert('No se pudo confirmar la reserva. Intenta nuevamente.');
        }
    }

    if (!reservationData) {
        return <Typography>Cargando datos...</Typography>;
    }
    
    return (
        <Box p={4}>
            <Typography variant="h5"sx={{ mb: 3 }}> Descuento especial para fines de semana/feriados </Typography>
            <TextField
                label="Descuento(%)"
                type="number"
                value={(specialDaysDiscount * 100).toFixed(0)}
                onChange={(e) => {
                    const value = parseFloat(e.target.value);
                    setSpecialDaysDiscount(isNaN(value) ? 0 : value / 100);
                }}
                onBlur={() => {
                    // Cuando el usuario deja de editar, vuelve a simular todos los recibos
                    simulateAllReceipts();
                }}
                slotProps={{
                    input: {
                      min: 0,
                      max: 100,
                    },
                  }}
                sx={{ mb: 6 }}
            />
            <Typography variant="h4"sx={{ mb: 3 }}>Resumen de la Reserva</Typography>
            <Typography variant="h5">Fecha: {reservationData.dateReservation}</Typography>
            <Typography variant="h5">Hora de inicio: {reservationData.startHourReservation}</Typography>
            <Typography variant="h5">Hora final: {reservationData.finalHourReservation}</Typography>
            <Typography variant="h5"sx={{ mb: 4 }}>Número de vueltas: {reservationData.turnsTimeReservation}</Typography>

            {simulatedReceipts.length > 0 ? (
                simulatedReceipts.map((receipt, idx) => (
                    <Box key={idx} my={2} p={2} border="1px solid #ccc" borderRadius={2}>
                        <Typography variant="h5" textAlign="left">Nombre: {receipt.nameClientReceipt}</Typography>
                        <Typography sx={{ mb: 1 }}textAlign="left">RUT: {receipt.rutClientReceipt}</Typography> 
                        
                        <Box 
                            sx={{ 
                                borderTop: '1px solid #ccc', 
                                my: 2  // margen arriba y abajo
                            }} 
                        />

                        <Typography textAlign="left">Precio base: ${receipt.baseRateReceipt.toFixed(0)}</Typography>
                        <Typography textAlign="left">Descuento por numero del grupo: {(receipt.groupSizeDiscount* 100).toFixed(0)}%</Typography>
                        <Typography textAlign="left">Descuento por cumpleaños: {(receipt.birthdayDiscount* 100).toFixed(0)}%</Typography>
                        <Typography textAlign="left">Descuento por cliente frecuente: {(receipt.loyaltyDiscount* 100).toFixed(0)}%</Typography>
                        <Typography textAlign="left">Descuento por fines de semana/feriados: {(receipt.specialDaysDiscount* 100).toFixed(0)}%</Typography>
                        <Typography textAlign="left" color="#2196f3">Descuento máximo aplicado: {(receipt.maxDiscount * 100).toFixed(0)}%</Typography>
                        <Typography textAlign="left">Monto final (descuento aplicado): ${receipt.finalAmount.toFixed(0)}</Typography>
                        <Typography textAlign="left">IVA (19%): ${receipt.ivaAmount.toFixed(0)}</Typography>

                        <Box 
                            sx={{ 
                                borderTop: '1px solid #ccc', 
                                my: 2  // margen arriba y abajo
                            }} 
                        />

                        <Typography 
                            variant="h6"
                            sx={{ 
                                color: '#4caf50',  // verde bonito
                                fontWeight: 'bold',
                                mt: 2, // margen arriba
                                fontSize: '1.7rem', // bien grande
                                textAlign: 'center'
                                }}
                            >
                                Total a pagar: ${receipt.totalAmount.toFixed(0)}
                        </Typography>
                    </Box>
                    
                ))
            ): (
                <Typography>Cargando simulaciones...</Typography>
            )
        }
            
            {/* Botón para confirmar */}
            <Button
                variant="contained"
                color="success"
                // aqui cuando se hace click se llama a la función handleSubmitReservation
                onClick={() => { handleSubmitReservation();}}
            >
                Confirmar Reserva
            </Button>
        </Box>
    );
}
