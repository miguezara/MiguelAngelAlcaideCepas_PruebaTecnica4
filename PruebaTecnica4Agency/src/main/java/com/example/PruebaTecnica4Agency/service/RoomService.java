package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.RoomDTO;
import com.example.PruebaTecnica4Agency.model.Room;
import com.example.PruebaTecnica4Agency.repository.HotelRepository;
import com.example.PruebaTecnica4Agency.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public Room saveRoom(Room room) {
        String codHotel = String.valueOf(room.getHotel().getHotelCode());

        // Verificar si el hotel existe antes de guardar la habitación
        if (!hotelRepository.existsById(Long.valueOf(codHotel))) {
            throw new NoSuchElementException("Hotel with code " + codHotel + " not found");
        }

        // Verificar si la habitación ya existe
        if (roomRepository.existsById(Long.valueOf(room.getRoomCode()))) {
            throw new IllegalArgumentException("Room with code " + room.getRoomCode() + " already exists");
        }

        return roomRepository.save(room); // Guardar la habitación
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room findRoomById(String codRoom) {
        return roomRepository.findById(Long.valueOf(codRoom)).orElse(null);
    }

    @Override
    public Room updateRoom(String codRoom, RoomDTO roomDTO) {
        Room existingRoom = roomRepository.findById(Long.valueOf(codRoom))
                .orElseThrow(() -> new NoSuchElementException("Room with code " + codRoom + " not found"));

        // Actualizar los atributos de la habitación con los valores del DTO
        existingRoom.setAvailableFrom(roomDTO.getAvailableFrom());
        existingRoom.setAvailableUntil(roomDTO.getAvailableUntil());
        existingRoom.setNumBed(roomDTO.getNumBed());
        existingRoom.setAvailableRoom(roomDTO.getAvailableRoom());
        existingRoom.setPricePerNight(roomDTO.getPricePerNight());

        return roomRepository.save(existingRoom); // Guardar y devolver la habitación actualizada
    }

    @Override
    public Room deleteRoom(String codRoom) {
        Room room = roomRepository.findById(Long.valueOf(codRoom))
                .orElseThrow(() -> new NoSuchElementException("Room with code " + codRoom + " not found"));

        roomRepository.delete(room);
        return room; // Devolver la habitación eliminada
    }
}
