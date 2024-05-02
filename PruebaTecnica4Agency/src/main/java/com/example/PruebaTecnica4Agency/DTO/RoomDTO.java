package com.example.PruebaTecnica4Agency.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO  {
    private String roomCode;
    private LocalDate availableFrom;
    private LocalDate availableUntil;
    private Boolean reserved;
    private int numBed;
    private Boolean availableRoom;
    private Double pricePerNight;


}
