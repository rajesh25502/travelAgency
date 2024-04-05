package com.example.TravelAgency.Controller;

import com.example.TravelAgency.DTO.AllPassengerResponse;
import com.example.TravelAgency.DTO.ItineraryResponse;
import com.example.TravelAgency.DTO.PassengerResponse;
import com.example.TravelAgency.Entity.Activities;
import com.example.TravelAgency.Entity.Passenger;
import com.example.TravelAgency.Entity.TravelPackage;
import com.example.TravelAgency.Entity.User;
import com.example.TravelAgency.Service.TravelPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/travelagency")
public class UserController {
    @Autowired
    private TravelPassengerService travelPassengerService;

    @PostMapping("/addPassenger/{packageID}")
    public ResponseEntity<Passenger> addPassenger(@RequestBody Passenger passenger,
                                                  @PathVariable long packageID)throws Exception
    {
       Passenger passenger1=travelPassengerService.addPassenger(passenger,packageID);
        return new ResponseEntity<>(passenger1, HttpStatus.CREATED);
    }

    @GetMapping("/allPackage")
    public ResponseEntity<List<TravelPackage>> getAllPackages()throws Exception{
        List<TravelPackage> packages=travelPassengerService.getAllPackages();
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }
    @PutMapping("/enroll/{passengerID}/{activityID}")
    public ResponseEntity<Passenger> enrollForActivity(@PathVariable long passengerID,
                                                  @PathVariable long activityID)throws Exception{
        Passenger passenger=travelPassengerService.enrollForActivity(passengerID,activityID);
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @GetMapping("/getItinerary/{packageID}")
    public ResponseEntity<ItineraryResponse> getItineraryOoPackage(@PathVariable Long packageID)throws Exception
    {
        ItineraryResponse response=travelPassengerService.getItineraryOfPackage(packageID);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{packageID}/passengers")
    public ResponseEntity<PassengerResponse> getAllPassangerOfPAckage(@PathVariable long packageID)throws Exception{
        PassengerResponse passenger=travelPassengerService.getAllPassengerOfPackage(packageID);
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }
    @PutMapping("/addBalance/{passengerID}")
    public ResponseEntity<Passenger> addBalance(@PathVariable long passengerID,
                                           @RequestParam double amount)throws Exception{
        Passenger user=travelPassengerService.addBalance(amount,passengerID);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<AllPassengerResponse>> getAllPassengers()throws Exception{
        List<AllPassengerResponse> passengers=travelPassengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @GetMapping("/availableActivities")
    public ResponseEntity<List<Activities>> searchForAvailableActivities()throws Exception{
        List<Activities> activities=travelPassengerService.searchForAvailableActivities();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
}
