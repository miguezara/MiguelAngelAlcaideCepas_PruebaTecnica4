package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.FlightBookingDTO;
import com.example.PruebaTecnica4Agency.model.Client;
import com.example.PruebaTecnica4Agency.model.Flight;
import com.example.PruebaTecnica4Agency.model.FlightBooking;
import com.example.PruebaTecnica4Agency.repository.FlightBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightBookingService implements IFlightBookingService {


     FlightBookingRepository flightBookingRepository;


   IClientService clientService;


     IFlightService flightService;

    @Override
    public FlightBooking saveFlightBooking(FlightBooking flightBooking) {
        // Verificar si el vuelo está disponible para reservar
        if (isFlightAvailable(flightBooking)) {
            // Verificar si todos los clientes existen en la base de datos
            List<Client> clients = flightBooking.getClients();
            for (Client client : clients) {
                if (clientService.findClientById(client.getDni()) == null) {
                    // Si un cliente no existe, no se puede hacer la reserva
                    return null;
                }
            }
            // Guardar la reserva del vuelo
            return flightBookingRepository.save(flightBooking);
        } else {
            // Si el vuelo no está disponible, retornar null
            return null;
        }
    }

    @Override
    public List<FlightBooking> findAllFlightBooking() {
        return flightBookingRepository.findAll();
    }

    @Override
    public FlightBooking findFlightBookingById(Long codFlightBooking) {
        return flightBookingRepository.findById(codFlightBooking).orElse(null);
    }

    @Override
    public FlightBooking updateFlightBooking(Long codFlightBooking, FlightBookingDTO flightBookingDTO) {
        // Buscar la reserva del vuelo por su código
        FlightBooking existingFlightBooking = flightBookingRepository.findById(codFlightBooking).orElse(null);
        if (existingFlightBooking != null) {
            // Actualizar los campos necesarios de la reserva del vuelo
            existingFlightBooking.setSeatType(flightBookingDTO.getSeatType());
            existingFlightBooking.setTotalPrice(flightBookingDTO.getSeatPrice());
            // Guardar los cambios en la reserva del vuelo
            return flightBookingRepository.save(existingFlightBooking);
        }
        // Si la reserva del vuelo no existe, retornar null
        return null;
    }

    @Override
    public FlightBooking deleteFlightBooking(Long codFlightBooking) {
        // Verificar si la reserva del vuelo existe
        if (existingFlightBooking(codFlightBooking)) {
            // Buscar la reserva del vuelo por su código
            FlightBooking flightBooking = flightBookingRepository.findById(codFlightBooking).orElse(null);
            if (flightBooking != null) {
                // Eliminar la reserva del vuelo de la base de datos
                flightBookingRepository.delete(flightBooking);
                // Retornar la reserva del vuelo eliminada
                return flightBooking;
            }
        }
        // Si la reserva del vuelo no existe, retornar null
        return null;
    }

    // Métodos auxiliares

    // Verificar si el vuelo está disponible para la fecha deseada
    private boolean isFlightAvailable(FlightBooking flightBooking) {
        Flight flight = flightService.findFlightById(flightBooking.getFlight().getFlightCode());
        if (flight != null) {
            LocalDate flightDate = flightBooking.getFlightDate();
            // Verificar si la fecha de vuelo está disponible
            return flightDate != null && isFlightAvailableForDate(flight, flightDate)
                    // Verificar si el número de personas no excede la capacidad máxima del vuelo
                    && flightBooking.getNumPersons() <= flight.getPeopleQ();
        }
        // Si el vuelo no existe, retornar false
        return false;
    }

    // Verificar si el vuelo está disponible para la fecha deseada
    private boolean isFlightAvailableForDate(Flight flight, LocalDate flightDate) {
        LocalDate availableFrom = flight.getFlightDate();
        // El vuelo está disponible si la fecha de vuelo coincide o es posterior a la fecha disponible
        return flightDate.isEqual(availableFrom) || flightDate.isAfter(availableFrom);
    }

    // Verificar si la reserva del vuelo existe
    private boolean existingFlightBooking(Long codFlightBooking) {
        return flightBookingRepository.existsById(codFlightBooking);
    }
}
