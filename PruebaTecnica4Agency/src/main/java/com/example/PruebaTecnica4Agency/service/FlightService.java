package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.model.Flight;
import com.example.PruebaTecnica4Agency.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService implements IFlightService {

    private final FlightRepository flightRepository;

    // Método para guardar un nuevo vuelo
    @Override
    public Flight saveFlight(Flight flight) {
        // Verificar si el vuelo ya existe
        if (existFlight(flight.getFlightCode())) {
            throw new IllegalArgumentException("Flight already exists");
        }
        return flightRepository.save(flight);
    }

    // Método para obtener todos los vuelos
    @Override
    public List<Flight> findAllFlight() {
        return flightRepository.findAll();
    }

    // Método para encontrar un vuelo por su código
    @Override
    public Flight findFlightById(String codFlight) {
        try {
            long flightId = Long.parseLong(codFlight);
            return flightRepository.findById(flightId).orElse(null);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid flight code provided", e);
        }
    }

    // Método para actualizar un vuelo
    @Override
    public Flight updateFlight(String codFlight, Flight updatedFlight) {
        try {
            long flightId = Long.parseLong(codFlight);
            if (existFlight(codFlight)) {
                Flight existingFlight = flightRepository.findById(flightId).orElse(null);
                if (existingFlight != null) {
                    // Actualizar los atributos del vuelo con los valores actualizados
                    existingFlight.setOrigin(updatedFlight.getOrigin());
                    existingFlight.setDestination(updatedFlight.getDestination());
                    existingFlight.setFlightDate(updatedFlight.getFlightDate());
                    existingFlight.setPeopleQ(updatedFlight.getPeopleQ());
                    existingFlight.setSeatPrice(updatedFlight.getSeatPrice());
                    return flightRepository.save(existingFlight);
                }
            }
            throw new IllegalArgumentException("Flight not found");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid flight code provided", e);
        }
    }

    // Método para eliminar un vuelo
    @Override
    public Flight deleteFlight(String codFlight) {
        try {
            long flightId = Long.parseLong(codFlight);
            if (existFlight(codFlight)) {
                Flight flight = flightRepository.findById(flightId).orElse(null);
                // Verificar si existen reservas asociadas al vuelo
                if (flight != null && existFlightBookings(flight)) {
                    flightRepository.delete(flight);
                    return flight;
                }
            }
            throw new IllegalArgumentException("Flight not found or has associated bookings");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid flight code provided", e);
        }
    }

    // Método para encontrar vuelos disponibles con origen y destino para un rango de fechas
    @Override
    public List<Flight> findAvailableFlightWithOriginAndDestinationForDates(String origin, String destination, LocalDate flightDate, LocalDate maxDate) {
        return flightRepository.findByOriginAndDestination(origin, destination).stream()
                .filter(flight -> flight.getFlightDate().isEqual(flightDate)
                        || (flight.getFlightDate().isAfter(flightDate) && flight.getFlightDate().isBefore(maxDate)))
                .collect(Collectors.toList());
    }

    // Método auxiliar para verificar si un vuelo existe con el código dado
    private boolean existFlight(String codFlight) {
        try {
            long flightId = Long.parseLong(codFlight);
            return flightRepository.existsById(flightId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid flight code provided", e);
        }
    }

    // Método auxiliar para verificar si un vuelo tiene reservas asociadas
    private boolean existFlightBookings(Flight flight) {
        return !flight.getFlightBookings().isEmpty();
    }
}
