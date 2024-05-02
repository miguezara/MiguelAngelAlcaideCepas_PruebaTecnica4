package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.RoomBookingDTO;
import com.example.PruebaTecnica4Agency.model.RoomBooking;

import java.util.List;

public interface IRoomBookingService {
    RoomBooking saveRoomBooking(RoomBooking roomBooking);

    List<RoomBooking> findAllRoomBooking();

    RoomBooking findRoomBookingById(Long codRoomBooking);

    RoomBooking updateRoomBooking(Long codRoomBooking, RoomBookingDTO roomBookingUpdateDTO);

    RoomBooking deleteRoomBooking(Long codRoomBooking);
}