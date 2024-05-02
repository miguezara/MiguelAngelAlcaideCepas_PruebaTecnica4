package com.example.PruebaTecnica4Agency.controller;

import com.example.PruebaTecnica4Agency.DTO.RoomBookingDTO;
import com.example.PruebaTecnica4Agency.model.RoomBooking;
import com.example.PruebaTecnica4Agency.service.IRoomBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/hotel-booking")
public class RoomBookingController {

    @Autowired
    private IRoomBookingService roomBookingService;

    @PostMapping
    public ResponseEntity<RoomBooking> saveRoomBooking(@RequestBody RoomBooking roomBooking) {
        RoomBooking savedRoomBooking = roomBookingService.saveRoomBooking(roomBooking);

        if (savedRoomBooking != null) {
            return new ResponseEntity<>(savedRoomBooking, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<String> createHotelBooking(@RequestBody RoomBooking roomBooking) {
        RoomBooking savedRoomBooking = roomBookingService.saveRoomBooking(roomBooking);

        if (savedRoomBooking != null) {
            return new ResponseEntity<>("Hotel booking created successfully. Total amount: " + savedRoomBooking.getRoomPrice(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Unable to create hotel booking. Please check availability and input data.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<RoomBooking>> getAllRoomBookings() {
        List<RoomBooking> roomBookings = roomBookingService.findAllRoomBooking();
        if (!roomBookings.isEmpty()) {
            return new ResponseEntity<>(roomBookings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{roomBookingId}")
    public ResponseEntity<RoomBooking> getRoomBookingById(@PathVariable long roomBookingId) {
        RoomBooking roomBooking = roomBookingService.findRoomBookingById(roomBookingId);
        if (roomBooking != null) {
            return new ResponseEntity<>(roomBooking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{roomBookingId}")
    public ResponseEntity<RoomBooking> updateRoomBooking(@PathVariable long roomBookingId, @RequestBody RoomBookingDTO roomBookingDTO) {
        RoomBooking updatedRoomBooking = roomBookingService.updateRoomBooking(roomBookingId, roomBookingDTO);

        if (updatedRoomBooking != null) {
            return new ResponseEntity<>(updatedRoomBooking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{roomBookingId}")
    public ResponseEntity<String> deleteRoomBooking(@PathVariable long roomBookingId) {
        RoomBooking roomBooking = roomBookingService.deleteRoomBooking(roomBookingId);
        if (roomBooking != null) {
            return new ResponseEntity<>("Room booking deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Room booking not found", HttpStatus.NOT_FOUND);
        }
    }
}
