package com.example.PruebaTecnica4Agency.controller;


import com.example.PruebaTecnica4Agency.DTO.RoomDTO;
import com.example.PruebaTecnica4Agency.model.Room;
import com.example.PruebaTecnica4Agency.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/hotel/rooms")
public class RoomController {

    @Autowired
    IRoomService roomService;

    @PostMapping("/new")
    public ResponseEntity<Room> saveRoom(@RequestBody Room room) {
        Room savedRoom = roomService.saveRoom(room);
        if (savedRoom != null) {
            return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.findAllRooms();
        if (!rooms.isEmpty()) {
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{roomCode}")
    public ResponseEntity<Room> getRoomByCode(@PathVariable String roomCode) {
        Room room = roomService.findRoomById(roomCode);
        if (room != null) {
            return new ResponseEntity<>(room, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{roomCode}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomCode, @RequestBody RoomDTO roomDTO) {
        Room updatedRoom = roomService.updateRoom(roomCode, roomDTO);
        if (updatedRoom != null) {
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{roomCode}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomCode) {
        Room deletedRoom = roomService.deleteRoom(roomCode);
        if (deletedRoom != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
