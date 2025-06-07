import React, { useEffect, useState } from 'react';
import { getAllKarts } from '../services/kart.service';
import {
    Container,
    Typography,
    Grid,
    Box,
    CircularProgress,
    AlertTitle,
    Alert,
} from '@mui/material';

const Karts = () => {
    // "karts" es una variable de estado que empieza como un array vacío
    // la funcion setKarts se usa para actualizar el estado de "karts"
    const [karts, setKarts] = useState([]);

    // "loading" es una variable de estado que empieza como true
    // para que cuando se este cargando los karts, se muestre un mensaje de carga
    const [loading, setLoading] = useState(true);

    // "error" es una variable de estado que empieza como null
    // si ocurre un error al cargar los karts, se actualiza con un mensaje de error
    const [error, setError] = useState(null);

    // cuando se carga haz lo siguiente
    useEffect(() => {
        getAllKarts() // hace una llamada a la API para obtener todos los karts
            .then((response) => {
                // si la llamada es exitosa
                setKarts(response.data); // guarda los datos
                setLoading(false); // indica que ya terminó la carga
            })
            .catch((err) => {
                // sino
                setError('Error con la base de datos al cargar los karts.');
                setLoading(false); // deja de cargar
            });
    }, []); // [] significa que solo se ejecuta una vez al cargar el componente

    if (loading) {
        return (
            <Box
                sx={{
                    position: 'fixed',
                    top: 0,
                    left: 0,
                    width: '100vw',
                    height: '100vh',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    backgroundColor: 'rgba(0, 0, 0, 0.21)',
                    zIndex: 1300,
                }}
            >
                <CircularProgress color="secondary" sx={{ mr: 2 }} />
                <Typography variant="h6" color="rgba(126, 126, 126, 0.84)">
                    Cargando karts...
                </Typography>
            </Box>
        );
    }

    if (error) {
        return (
            <Box
                sx={{
                    position: 'fixed',
                    top: 0,
                    left: 0,
                    width: '100vw',
                    height: '100vh',
                    display: 'flex',

                    justifyContent: 'center',
                    alignItems: 'center',
                }}
            >
                <Alert severity="error">
                    <AlertTitle>Error</AlertTitle>
                    {error}
                </Alert>
            </Box>
        );
    }

    return (
        <Container sx={{ mt: 10 }}>
            <Typography variant="h4" fontWeight="bold" sx={{ mb: 3, textAlign: 'left' }}>
                Karts Disponibles
            </Typography>

            <Grid container spacing={3}>
                {karts.map((kart) => (
                    <Grid key={kart.idKart}>
                        <Box
                            sx={{
                                border: '0.7px solid #ccc',
                                borderRadius: '10px',
                                padding: 3,
                                width: 280,
                                height: 110,
                                backgroundColor: '#708090',
                                overflow: 'hidden',
                            }}
                        >
                            <Typography sx={{ mb: 1 }}>código: {kart.codeKart}</Typography>
                            <Typography sx={{ mb: 1 }}>Modelo: {kart.modelKart}</Typography>
                            <Typography sx={{ mb: 1 }}>Estado: {kart.statusKart}</Typography>
                        </Box>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default Karts;
