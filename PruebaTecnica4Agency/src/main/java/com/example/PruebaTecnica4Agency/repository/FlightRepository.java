package com.example.PruebaTecnica4Agency.repository;

import com.example.PruebaTecnica4Agency.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {


    List<Flight> findByOriginAndDestination(String origin, String destination);
}
