package com.example.TravelAgency.DTO;

import com.example.TravelAgency.Entity.Destinations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryResponse {
    private String packageName;
    private List<Destinations> destination;
}
