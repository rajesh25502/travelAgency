package com.example.TravelAgency.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private long capacity;

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Destinations> itinerary;

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Passenger> passengers;

}
