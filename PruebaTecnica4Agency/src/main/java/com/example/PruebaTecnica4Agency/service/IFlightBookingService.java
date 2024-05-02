package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.FlightBookingDTO;
import com.example.PruebaTecnica4Agency.model.FlightBooking;

import java.util.List;

public interface IFlightBookingService {
    FlightBooking saveFlightBooking(FlightBooking flightBooking);

    List<FlightBooking> findAllFlightBooking();

    FlightBooking findFlightBookingById(Long codFlightBooking);

    FlightBooking updateFlightBooking(Long codFlightBooking, FlightBookingDTO flightBookingDTO);



    FlightBooking deleteFlightBooking(Long codFlightBooking);

}
