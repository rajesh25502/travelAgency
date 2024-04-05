package com.example.TravelAgency.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private SubscriptionType subscriptionType;

    private double balance;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_activities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activities_id"))
    private List<Activities> activities;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "travel_package_id")
    private TravelPackage travelPackage;
}
