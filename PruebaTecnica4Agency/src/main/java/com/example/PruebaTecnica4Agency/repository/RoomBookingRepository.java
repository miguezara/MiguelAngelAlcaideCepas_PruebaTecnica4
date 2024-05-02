package com.example.PruebaTecnica4Agency.repository;

import com.example.PruebaTecnica4Agency.model.Room;
import com.example.PruebaTecnica4Agency.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
}
