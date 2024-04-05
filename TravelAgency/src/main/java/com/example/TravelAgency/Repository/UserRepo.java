package com.example.TravelAgency.Repository;

import com.example.TravelAgency.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    public User findByEmail(String userName);

    public List<User> findByActivitiesId(long activityID);
}
