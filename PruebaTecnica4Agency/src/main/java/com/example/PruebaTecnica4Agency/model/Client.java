package com.example.PruebaTecnica4Agency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dni;
    private String name;
    private String lastName;
    private String email;

    @JsonIgnore
    @ManyToMany(mappedBy = "clients")
    private List<RoomBooking> roomBookings = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "clients")
    private List<FlightBooking> flightBookings = new ArrayList<>();
}


