package com.example.TravelAgency.Repository;

import com.example.TravelAgency.Entity.Activities;
import com.example.TravelAgency.Entity.Destinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationsRepo extends JpaRepository<Destinations,Long> {

    public Destinations findByActivitiesId(Long activityID);
}
