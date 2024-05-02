package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.RoomBookingDTO;
import com.example.PruebaTecnica4Agency.model.Client;
import com.example.PruebaTecnica4Agency.model.Room;
import com.example.PruebaTecnica4Agency.model.RoomBooking;
import com.example.PruebaTecnica4Agency.repository.RoomBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoomBookingService implements IRoomBookingService {

    private final RoomBookingRepository roomBookingRepository;
    private final IClientService clientService;
    private final IRoomService roomService;

    @Override
    public RoomBooking saveRoomBooking(RoomBooking roomBooking) {
        if (isRoomAvailable(roomBooking)) {
            List<Client> validClients = new ArrayList<>();

            for (Client client : roomBooking.getClients()) {
                Client existingClient = clientService.findClientById(client.getDni());

                if (existingClient != null) {
                    validClients.add(existingClient);
                } else {
                    return null;
                }
            }

            double roomPricePerNight = roomBooking.getRoom().getPricePerNight();
            long nights = (long) roomBooking.getStayUntil().getDayOfMonth() - roomBooking.getStayFrom().getDayOfMonth();
            double totalAmount = roomPricePerNight * nights;

            roomBooking.setRoomPrice(totalAmount);
            roomBooking.setClients(validClients);

            return roomBookingRepository.save(roomBooking);
        } else {
            return null;
        }
    }


    @Override
    public List<RoomBooking> findAllRoomBooking() {
        return roomBookingRepository.findAll();
    }

    @Override
    public RoomBooking findRoomBookingById(Long codRoomBooking) {
        return roomBookingRepository.findById(codRoomBooking)
                .orElseThrow(() -> new NoSuchElementException("Room Booking with code " + codRoomBooking + " not found"));
    }

    @Override
    public RoomBooking updateRoomBooking(Long codRoomBooking, RoomBookingDTO roomBookingDTO) {
        RoomBooking existingRoomBooking = roomBookingRepository.findById(codRoomBooking).orElse(null);

        if (existingRoomBooking != null) {
            existingRoomBooking.setRoomPrice(roomBookingDTO.getRoomPrice());
            return roomBookingRepository.save(existingRoomBooking);
        } else {
            throw new NoSuchElementException("Room Booking with code " + codRoomBooking + " not found");
        }
    }

    @Override
    public RoomBooking deleteRoomBooking(Long codRoomBooking) {
        if (existRoomBooking(codRoomBooking)) {
            RoomBooking roomBooking = roomBookingRepository.findById(codRoomBooking).orElse(null);
            if (roomBooking != null) {
                roomBookingRepository.delete(roomBooking);
                return roomBooking;
            }
        }
        throw new NoSuchElementException("Room Booking with code " + codRoomBooking + " not found");
    }

    private boolean existRoomBooking(Long codRoomBooking) {
        return roomBookingRepository.existsById(codRoomBooking);
    }

    private boolean isRoomAvailable(RoomBooking roomBooking) {
        Room room = roomService.findRoomById(String.valueOf(roomBooking.getRoom().getRoomCode()));

        if (room != null && room.getRoomCode() != null) {
            LocalDate stayFrom = roomBooking.getStayFrom();
            LocalDate stayUntil = roomBooking.getStayUntil();

            if (stayFrom != null && stayUntil != null) {
                boolean isRoomAvailableForDates = isRoomAvailableForDates(room, stayFrom, stayUntil);
                int numPersons = roomBooking.getNumPersons();
                boolean isNumPersonsValid = numPersons <= room.getNumBed();
                boolean isRoomCurrentlyAvailable = room.getAvailableRoom();

                return isRoomAvailableForDates && isNumPersonsValid && isRoomCurrentlyAvailable;
            }
        }

        return false;
    }

    private boolean isRoomAvailableForDates(Room room, LocalDate stayFrom, LocalDate stayUntil) {
        if (!room.getAvailableRoom()) {
            return false;
        }

        LocalDate availableFrom = room.getAvailableFrom();
        LocalDate availableUntil = room.getAvailableUntil();

        for (RoomBooking existingBooking : room.getRoomBookings()) {
            LocalDate existingStayFrom = existingBooking.getStayFrom();
            LocalDate existingStayUntil = existingBooking.getStayUntil();

            if ((stayFrom.isBefore(existingStayUntil) || stayFrom.isEqual(existingStayUntil))
                    && (stayUntil.isAfter(existingStayFrom) || stayUntil.isEqual(existingStayFrom))) {
                return false;
            }
        }

        return (stayFrom.isEqual(availableFrom) || (stayFrom.isAfter(availableFrom) && stayFrom.isBefore(availableUntil)))
                && (stayUntil.isEqual(availableUntil) || (stayUntil.isAfter(availableFrom) && stayUntil.isBefore(availableUntil)));
    }
}
