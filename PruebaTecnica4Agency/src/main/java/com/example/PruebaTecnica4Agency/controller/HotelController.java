package com.example.PruebaTecnica4Agency.controller;


import com.example.PruebaTecnica4Agency.DTO.HotelDTO;


import com.example.PruebaTecnica4Agency.model.Hotel;
import com.example.PruebaTecnica4Agency.service.IHotelService;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/agency/hotels")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @PostMapping("/new")
    public ResponseEntity<Object> saveHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.saveHotel(hotel);
        HttpStatus status = (savedHotel != null) ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body((savedHotel != null) ? savedHotel : "Unable to create hotel. Please check input data.");
    }

    @GetMapping
    public ResponseEntity<Object> getAllHotels(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {

        LocalDate parsedDateFrom = null;
        LocalDate parsedDateTo = null;

        if (dateFrom != null && dateTo != null) {
            try {
                parsedDateFrom = LocalDate.parse(dateFrom);
                parsedDateTo = LocalDate.parse(dateTo);
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body("Invalid date format. Please provide dates in yyyy-MM-dd format.");
            }
        }

        List<Hotel> hotels = hotelService.findAvalaibleHotelsInCityForDates(city, parsedDateFrom, parsedDateTo);
        return (hotels.isEmpty()) ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(hotels);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
        Hotel hotel = hotelService.findHotelById(hotelId);
        return (hotel != null) ? ResponseEntity.ok().body(hotel) : ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{hotelId}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable String hotelId, @RequestBody HotelDTO updatedHotel) {
        Hotel savedHotel = hotelService.updateHotel(hotelId, updatedHotel);
        return (savedHotel != null) ? ResponseEntity.ok().body(savedHotel) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable String hotelId) {
        Hotel deletedHotel = hotelService.deleteHotel(hotelId);

        if (deletedHotel != null) {
            return new ResponseEntity<>("Hotel deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to delete hotel. Either hotel not found or there are reservations in its rooms.", HttpStatus.CONFLICT);
        }
    }
}

