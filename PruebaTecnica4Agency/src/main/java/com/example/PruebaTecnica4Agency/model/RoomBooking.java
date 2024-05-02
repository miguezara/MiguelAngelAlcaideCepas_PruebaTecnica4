package com.example.PruebaTecnica4Agency.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numPersons;
    private LocalDate stayFrom;
    private LocalDate stayUntil;
    private Double roomPrice;

    @ManyToOne
    @JoinColumn(name = "room_code")
    @JsonBackReference
    private Room room;

    @ManyToMany
    @JoinTable(
            name = "client_room_booking",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "room_booking_id")
    )
    private List<Client> clients = new ArrayList<>();
}
