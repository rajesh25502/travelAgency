package com.example.TravelAgency.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String activityName;

    private String description;

    private double cost;

    private long capacity;

    private long available;

    @JsonIgnore
    @ManyToOne
    private Destinations destination;

}
