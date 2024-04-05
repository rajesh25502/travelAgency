package com.example.TravelAgency.Repository;

import com.example.TravelAgency.Entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepo extends JpaRepository<Passenger,Long> {

}
