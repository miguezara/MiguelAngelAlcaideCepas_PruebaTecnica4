package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.HotelDTO;


import com.example.PruebaTecnica4Agency.model.Hotel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public interface IHotelService {


        Hotel saveHotel(Hotel hotel);

        List<Hotel> findAllHotel();

        Hotel findHotelById(String codHotel);



        Hotel updateHotel(String codHotel, HotelDTO hotelDTO);

        Hotel deleteHotel(String codHotel);

        List<Hotel> findAvalaibleHotelsInCityForDates(String city, LocalDate parsedDateFrom, LocalDate parsedDateTo);

    }



