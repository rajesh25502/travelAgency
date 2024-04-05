package com.example.TravelAgency.DTO;

import com.example.TravelAgency.Entity.SubscriptionType;
import com.example.TravelAgency.Entity.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {

    private  String jwt;
    private String message;
    private USER_ROLE role;
    private String userName;
}
