package com.example.PruebaTecnica4Agency.controller;

import com.example.PruebaTecnica4Agency.DTO.FlightBookingDTO;
import com.example.PruebaTecnica4Agency.model.FlightBooking;
import com.example.PruebaTecnica4Agency.service.IFlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/agency/flight-bookings")
public class FlightBookingController {



        @Autowired
        IFlightBookingService flightBookingService;

        @PostMapping
        public ResponseEntity<FlightBooking> saveFlightBooking(@RequestBody FlightBooking flightBooking) {
            FlightBooking savedFlightBooking = flightBookingService.saveFlightBooking(flightBooking);

            if (savedFlightBooking != null) {
                return new ResponseEntity<>(savedFlightBooking, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        @PostMapping("/new")
        public ResponseEntity<String> createFlightBooking(@RequestBody FlightBooking flightBooking) {
            FlightBooking savedFlightBooking = flightBookingService.saveFlightBooking(flightBooking);

            if (savedFlightBooking != null) {
                return new ResponseEntity<>("Flight booking created successfully. Total amount: " + savedFlightBooking.getTotalPrice(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Unable to create flight booking. Please check availability and input data.", HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping
        public ResponseEntity<List<FlightBooking>> getAllFlightBookings() {
            List<FlightBooking> flightBookings = flightBookingService.findAllFlightBooking();
            if (!flightBookings.isEmpty()) {
                return new ResponseEntity<>(flightBookings, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @PutMapping("/{flightBookingId}")
        public ResponseEntity<FlightBooking> updateFlightBooking(@PathVariable long flightBookingId, @RequestBody FlightBookingDTO flightBookingDTO) {

            FlightBooking updatedFlightBooking = flightBookingService.updateFlightBooking(flightBookingId, flightBookingDTO);

            if (updatedFlightBooking != null) {
                return new ResponseEntity<>(updatedFlightBooking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @DeleteMapping("/{flightBookingId}")
        public ResponseEntity<String> deleteFlightBooking(@PathVariable long flightBookingId) {
            FlightBooking flightBooking = flightBookingService.deleteFlightBooking(flightBookingId);
            if (flightBooking != null) {
                return new ResponseEntity<>("Flight booking deleted succesfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Flight booking not found", HttpStatus.NOT_FOUND);
            }
        }


    }
