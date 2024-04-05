package com.example.TravelAgency.DTO;

import com.example.TravelAgency.Entity.Passenger;
import com.example.TravelAgency.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerResponse {
    private String packageName;
    private long capacity;
    private long passengersEnrolled;
    private List<Passenger> passengerList;
}
