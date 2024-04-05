package com.example.TravelAgency.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllPassengerResponse {
    private String passengerName;
    private Long passengerID;
    private Double balance;

    private List<Activity> activityList;
}
