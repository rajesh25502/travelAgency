package com.example.TravelAgency.Repository;

import com.example.TravelAgency.Entity.Destinations;
import com.example.TravelAgency.Entity.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPackageRepo extends JpaRepository<TravelPackage,Long> {

    public TravelPackage findByItineraryId(Long destinationID);
}
