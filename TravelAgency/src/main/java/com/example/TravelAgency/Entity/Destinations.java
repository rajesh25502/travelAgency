package com.example.TravelAgency.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Destinations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String destinationName;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Activities> activities;

    @JsonIgnore
    @ManyToOne
    private TravelPackage travelPackage;
}
