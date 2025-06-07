import React from 'react';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import { Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import { getAllReservations } from '../services/reservation.service';
import { getReceiptsByReservationId } from '../services/receipt.service';

export default function Reports() {
    const [value, setValue] = React.useState('one');
    const [reportData, setReportData] = React.useState({ // de enero a mayo para los reportes
        turns: {
            10: { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
            15: { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
            20: { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
        },
        people: {
            '1-2': { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
            '3-5': { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
            '6-10': { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
            '11-15': { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
        }
    });

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    React.useEffect(() => {
        generateReport();
    }, []);

    async function generateReportByTurns(reservations) {
        const months = ['01', '02', '03', '04', '05']; // Meses
        const data = {
            10: { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
            15: { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
            20: { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
        };
    
        for (const res of reservations) {
            const reservationMonth = String(new Date(res.dateReservation).getMonth() + 1).padStart(2, '0'); // Mes de la reserva
            const turns = res.turnsTimeReservation; // Número de vueltas
            
            // verifica si el mes y el número de vueltas son válidos
            if (months.includes(reservationMonth) && (turns === 10 || turns === 15 || turns === 20)) {
                const receipts = await getReceiptsByReservationId(res.idReservation);
    
                // verifica si los recibos existen y tienen baseRateReceipt
                if (receipts && receipts.length > 0) {
                    for (const receipt of receipts) {
                        if (receipt.baseRateReceipt) {
                            data[turns][reservationMonth] += receipt.baseRateReceipt; // Acumula baseRateReceipt en el mes correspondiente
                        }
                    }
                }
            }
        }
    
        return data;
    }

    async function generateReportByPeople(reservations) {
        const months = ['01', '02', '03', '04', '05']; // Meses
        const data = {
          '1-2': { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
          '3-5': { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
          '6-10': { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
          '11-15': { '01': 0, '02': 0, '03': 0, '04': 0, '05': 0 },
        };
      
        for (const res of reservations) {
          const reservationMonth = String(new Date(res.dateReservation).getMonth() + 1).padStart(2, '0'); // Obtener mes
          const groupSize = res.groupSizeReservation; // número de personas
      
          // Clasificar el tamaño del grupo en rangos
          let peopleRange = '';
          if (groupSize >= 1 && groupSize <= 2) {
            peopleRange = '1-2';
          } else if (groupSize >= 3 && groupSize <= 5) {
            peopleRange = '3-5';
          } else if (groupSize >= 6 && groupSize <= 10) {
            peopleRange = '6-10';
          } else if (groupSize >= 11 && groupSize <= 15) {
            peopleRange = '11-15';
          }
      
          if (months.includes(reservationMonth) && peopleRange) {
            const receipts = await getReceiptsByReservationId(res.idReservation);
            
            // verifica si los recibos existen y tienen baseRateReceipt
            if (receipts && receipts.length > 0) {
              for (const receipt of receipts) {
                if (receipt.baseRateReceipt) {
                  data[peopleRange][reservationMonth] += receipt.baseRateReceipt;
                }
              }
            }
          }
        }
      
        return data;
    }

    async function generateReport() {
        try {

            const reservations = await getAllReservations();

            // Llamar a la función para obtener los datos por vueltas
            const dataByTurns = await generateReportByTurns(reservations);

            // Llamar a la función para obtener los datos por número de personas
            const dataByPeople = await generateReportByPeople(reservations);

            // Combinar los resultados de ambos reportes
            const combinedData = {
                turns: dataByTurns,
                people: dataByPeople,
            };

            setReportData(combinedData); // Establece los datos del reporte
        } catch (error) {
            console.error('Error al generar el reporte:', error);
        }
    }

    return (
        <Box
            sx={{
                width: '100%',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'flex-start',
                justifyContent: 'flex-start',
                minHeight: '100vh',
                mt: 2,
                ml: 2,
            }}
        >
            <Typography variant="h6" textAlign="left" sx={{ mb: 2 }}>
                Reportes según:
            </Typography>

            <Tabs
                value={value}
                onChange={handleChange}
                textColor="secondary"
                indicatorColor="secondary"
                aria-label="secondary tabs example"
            >
                <Tab value="one" label="número de vueltas/tiempo" />
                <Tab value="two" label="número de personas" />
            </Tabs>

            {value === 'one' && (
                <Box mt={4} sx={{ width: '90%' }}>
                    <Typography variant="h5" sx={{ mb: 2 }}>
                        Reporte de Ingresos por Vueltas o Tiempo
                    </Typography>

                    {reportData && (
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead sx={{ backgroundColor: '#424242' }}>
                                    <TableRow>
                                        <TableCell><b>Tipo</b></TableCell>
                                        <TableCell><b>Enero</b></TableCell>
                                        <TableCell><b>Febrero</b></TableCell>
                                        <TableCell><b>Marzo</b></TableCell>
                                        <TableCell><b>Abril</b></TableCell>
                                        <TableCell><b>Mayo</b></TableCell>
                                        <TableCell><b>Total</b></TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {['10', '15', '20'].map((turns) => (
                                        <TableRow key={turns}>
                                            <TableCell>{turns} vueltas o máx {turns} min</TableCell>
                                            <TableCell>${reportData.turns[turns]['01'] ? reportData.turns[turns]['01'].toLocaleString() : '0'}</TableCell>
                                            <TableCell>${reportData.turns[turns]['02'] ? reportData.turns[turns]['02'].toLocaleString() : '0'}</TableCell>
                                            <TableCell>${reportData.turns[turns]['03'] ? reportData.turns[turns]['03'].toLocaleString() : '0'}</TableCell>
                                            <TableCell>${reportData.turns[turns]['04'] ? reportData.turns[turns]['04'].toLocaleString() : '0'}</TableCell>
                                            <TableCell>${reportData.turns[turns]['05'] ? reportData.turns[turns]['05'].toLocaleString() : '0'}</TableCell>
                                            <TableCell>${(
                                                    (reportData.turns[turns]['01'] || 0) +
                                                    (reportData.turns[turns]['02'] || 0) +
                                                    (reportData.turns[turns]['03'] || 0) +
                                                    (reportData.turns[turns]['04'] || 0) +
                                                    (reportData.turns[turns]['05'] || 0)
                                                ).toLocaleString()}
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                    <TableRow sx={{ backgroundColor: '#424242' }}>
                                        <TableCell><b>TOTAL</b></TableCell>
                                        <TableCell><b>${['10', '15', '20'].reduce((sum, t) => sum + reportData.turns[t]['01'], 0).toLocaleString()}</b></TableCell>
                                        <TableCell><b>${['10', '15', '20'].reduce((sum, t) => sum + reportData.turns[t]['02'], 0).toLocaleString()}</b></TableCell>
                                        <TableCell><b>${['10', '15', '20'].reduce((sum, t) => sum + reportData.turns[t]['03'], 0).toLocaleString()}</b></TableCell>
                                        <TableCell><b>${['10', '15', '20'].reduce((sum, t) => sum + reportData.turns[t]['04'], 0).toLocaleString()}</b></TableCell>
                                        <TableCell><b>${['10', '15', '20'].reduce((sum, t) => sum + reportData.turns[t]['05'], 0).toLocaleString()}</b></TableCell>
                                        <TableCell><b>${['10', '15', '20'].reduce(
                                                    (sum, t) =>sum +
                                                        reportData.turns[t]['01'] +
                                                        reportData.turns[t]['02'] +
                                                        reportData.turns[t]['03'] +
                                                        reportData.turns[t]['04'] +
                                                        reportData.turns[t]['05'], 0
                                                ).toLocaleString()}</b>
                                        </TableCell>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </TableContainer>
                    )}
                </Box>
            )}
            {value === 'two' && (
                <Box mt={4} sx={{ width: '90%' }}>
                    <Typography variant="h5" sx={{ mb: 2 }}>
                    Reporte de Ingresos por Número de Personas
                    </Typography>

                    {reportData && (
                    <TableContainer component={Paper}>
                        <Table>
                        <TableHead sx={{ backgroundColor: '#424242' }}>
                            <TableRow>
                            <TableCell><b>Rango</b></TableCell>
                            <TableCell><b>Enero</b></TableCell>
                            <TableCell><b>Febrero</b></TableCell>
                            <TableCell><b>Marzo</b></TableCell>
                            <TableCell><b>Abril</b></TableCell>
                            <TableCell><b>Mayo</b></TableCell>
                            <TableCell><b>Total</b></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {['1-2', '3-5', '6-10', '11-15'].map((range) => (
                            <TableRow key={range}>
                                <TableCell>{range} personas</TableCell>
                                <TableCell>${reportData.people[range]['01'] ? reportData.people[range]['01'].toLocaleString() : '0'}</TableCell>
                                <TableCell>${reportData.people[range]['02'] ? reportData.people[range]['02'].toLocaleString() : '0'}</TableCell>
                                <TableCell>${reportData.people[range]['03'] ? reportData.people[range]['03'].toLocaleString() : '0'}</TableCell>
                                <TableCell>${reportData.people[range]['04'] ? reportData.people[range]['04'].toLocaleString() : '0'}</TableCell>
                                <TableCell>${reportData.people[range]['05'] ? reportData.people[range]['05'].toLocaleString() : '0'}</TableCell>
                                <TableCell>${(
                                (reportData.people[range]['01'] || 0) +
                                (reportData.people[range]['02'] || 0) +
                                (reportData.people[range]['03'] || 0) +
                                (reportData.people[range]['04'] || 0) +
                                (reportData.people[range]['05'] || 0)
                                ).toLocaleString()}</TableCell>
                            </TableRow>
                            ))}
                            <TableRow sx={{ backgroundColor: '#424242' }}>
                            <TableCell><b>TOTAL</b></TableCell>
                            <TableCell><b>${['1-2', '3-5', '6-10', '11-15'].reduce((sum, r) => sum + (reportData.people[r]['01'] || 0), 0).toLocaleString()}</b></TableCell>
                            <TableCell><b>${['1-2', '3-5', '6-10', '11-15'].reduce((sum, r) => sum + (reportData.people[r]['02'] || 0), 0).toLocaleString()}</b></TableCell>
                            <TableCell><b>${['1-2', '3-5', '6-10', '11-15'].reduce((sum, r) => sum + (reportData.people[r]['03'] || 0), 0).toLocaleString()}</b></TableCell>
                            <TableCell><b>${['1-2', '3-5', '6-10', '11-15'].reduce((sum, r) => sum + (reportData.people[r]['04'] || 0), 0).toLocaleString()}</b></TableCell>
                            <TableCell><b>${['1-2', '3-5', '6-10', '11-15'].reduce((sum, r) => sum + (reportData.people[r]['05'] || 0), 0).toLocaleString()}</b></TableCell>
                            <TableCell><b>${['1-2', '3-5', '6-10', '11-15'].reduce((sum, r) =>
                                sum +
                                (reportData.people[r]['01'] || 0) +
                                (reportData.people[r]['02'] || 0) +
                                (reportData.people[r]['03'] || 0) +
                                (reportData.people[r]['04'] || 0) +
                                (reportData.people[r]['05'] || 0), 0).toLocaleString()}</b></TableCell>
                            </TableRow>
                        </TableBody>
                        </Table>
                    </TableContainer>
                    )}
                </Box>
            )}
        </Box>
    );
}
