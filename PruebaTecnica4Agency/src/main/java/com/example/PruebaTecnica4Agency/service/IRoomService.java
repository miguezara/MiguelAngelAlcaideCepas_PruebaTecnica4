package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.RoomDTO;
import com.example.PruebaTecnica4Agency.model.Room;

import java.util.List;

public interface IRoomService {
    Room saveRoom(Room room);

    List<Room> findAllRooms();

    Room findRoomById(String codRoom);

    Room updateRoom(String codRoom, RoomDTO room);

    Room deleteRoom(String codRoom);
}

