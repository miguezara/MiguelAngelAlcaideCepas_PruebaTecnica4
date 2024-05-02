package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.HotelDTO;
import com.example.PruebaTecnica4Agency.model.Hotel;
import com.example.PruebaTecnica4Agency.model.Room;
import com.example.PruebaTecnica4Agency.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;

    // Método para guardar un nuevo hotel
    @Override
    public Hotel saveHotel(Hotel hotel) {
        // Verificar si el hotel es nulo o si ya existe
        if (hotel == null || existHotel(String.valueOf(hotel.getHotelCode()))) {
            throw new IllegalArgumentException("Hotel already exists or invalid hotel data provided");
        }
        return hotelRepository.save(hotel);
    }

    // Método para obtener todos los hoteles
    @Override
    public List<Hotel> findAllHotel() {
        return hotelRepository.findAll();
    }

    // Método para encontrar un hotel por su código
    @Override
    public Hotel findHotelById(String codHotel) {
        try {
            long hotelId = Long.parseLong(codHotel);
            return hotelRepository.findById(hotelId).orElse(null);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hotel code provided", e);
        }
    }

    // Método para actualizar un hotel
    @Override
    public Hotel updateHotel(String codHotel, HotelDTO hotelDTO) {
        try {
            long hotelId = Long.parseLong(codHotel);
            Hotel existingHotel = hotelRepository.findById(hotelId).orElse(null);
            // Si el hotel existe, actualizar los atributos con los valores del DTO
            if (existingHotel != null) {
                existingHotel.setHotelName(hotelDTO.getHotelName());
                existingHotel.setCity(hotelDTO.getCity());
                return hotelRepository.save(existingHotel);
            }
            throw new IllegalArgumentException("Hotel not found");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hotel code provided", e);
        }
    }

    // Método para eliminar un hotel
    @Override
    public Hotel deleteHotel(String codHotel) {
        try {
            long hotelId = Long.parseLong(codHotel);
            Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
            // Si el hotel existe y todas las habitaciones están vacías, eliminar el hotel
            if (hotel != null && areRoomsEmpty(hotel)) {
                hotelRepository.delete(hotel);
                return hotel;
            }
            throw new IllegalArgumentException("Hotel not found or rooms are not empty");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hotel code provided", e);
        }
    }

    // Método para encontrar hoteles disponibles en una ciudad para un rango de fechas
    @Override
    public List<Hotel> findAvalaibleHotelsInCityForDates(String city, LocalDate dateFrom, LocalDate dateTo) {
        return hotelRepository.findByCityIgnoreCase(city).stream()
                .filter(hotel -> hotel.getRooms().stream()
                        .anyMatch(room -> isRoomAvailableForDates(room, dateFrom, dateTo)))
                .toList();
    }

    // Método auxiliar para verificar si una habitación está disponible para un rango de fechas
    private boolean isRoomAvailableForDates(Room room, LocalDate dateFrom, LocalDate dateTo) {
        return room.getAvailableFrom().isBefore(dateTo) && room.getAvailableUntil().isAfter(dateFrom);
    }

    // Método auxiliar para verificar si existe un hotel con el código dado
    private boolean existHotel(String codHotel) {
        try {
            long hotelId = Long.parseLong(codHotel);
            return hotelRepository.existsById(hotelId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hotel code provided", e);
        }
    }

    // Método auxiliar para verificar si todas las habitaciones de un hotel están vacías
    private boolean areRoomsEmpty(Hotel hotel) {
        return hotel.getRooms().stream().allMatch(room -> room.getRoomBookings().isEmpty());
    }
}
