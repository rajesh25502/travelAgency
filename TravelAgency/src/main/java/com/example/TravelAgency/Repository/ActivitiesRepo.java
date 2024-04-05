package com.example.TravelAgency.Repository;

import com.example.TravelAgency.Entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivitiesRepo extends JpaRepository<Activities,Long> {
    @Query("SELECT a FROM Activities a WHERE a.available>0")
    public List<Activities> searchAvailableActivities();
}
