package com.example.TravelAgency.Service;

import com.example.TravelAgency.DTO.Activity;
import com.example.TravelAgency.DTO.AllPassengerResponse;
import com.example.TravelAgency.DTO.ItineraryResponse;
import com.example.TravelAgency.DTO.PassengerResponse;
import com.example.TravelAgency.Entity.*;
import com.example.TravelAgency.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TravelPassengerService {
    @Autowired
    private TravelPackageRepo travelPackageRepo;

    @Autowired
    private DestinationsRepo destinationsRepo;

    @Autowired
    private ActivitiesRepo activitiesRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PassengerRepo passengerRepo;

    public List<TravelPackage> getAllPackages()
    {
        return travelPackageRepo.findAll();
    }

    public Passenger addPassenger(Passenger passenger,long packageID)throws Exception
    {
        TravelPackage travelPackageInfo=getPackgeByID(packageID);

        Passenger passengerObject=new Passenger();
        passengerObject.setName(passenger.getName());
        passengerObject.setSubscriptionType(passenger.getSubscriptionType());
        passengerObject.setTravelPackage(travelPackageInfo);
        passengerObject.setBalance(passenger.getBalance());

        return passengerRepo.save(passengerObject);
    }
    public Passenger enrollForActivity(Long passengerID,Long activityID) throws Exception {


        Activities activitiyInfo=getActivityById(activityID);

        Passenger passengerInfo=getPassengerById(passengerID);

        if(activitiyInfo.getAvailable()<1)
        {
            throw new Exception("Activity has reached its capacity ");
        }
        activitiyInfo.setAvailable(activitiyInfo.getAvailable()-1);


        if(passengerInfo.getSubscriptionType().equals(SubscriptionType.STANDARD))
        {
            if(passengerInfo.getBalance()<activitiyInfo.getCost())
            {
                throw new Exception("No sufficient Balance, Add balance and try again.");
            }
            passengerInfo.setBalance(passengerInfo.getBalance()-activitiyInfo.getCost());
        }
        if(passengerInfo.getSubscriptionType().equals(SubscriptionType.GOLD))
        {
            if(passengerInfo.getBalance()<(activitiyInfo.getCost()*0.9))
            {
                throw new Exception("No sufficient Balance, Add balance and try again.");
            }
            passengerInfo.setBalance(passengerInfo.getBalance()-(activitiyInfo.getCost()*0.9));
        }
        passengerInfo.getActivities().add(activitiyInfo);

        return passengerRepo.save(passengerInfo);
    }
    public TravelPackage getPackgeByID(Long packageID)throws Exception
    {
        Optional<TravelPackage> travelPackage=travelPackageRepo.findById(packageID);
        if(travelPackage.isEmpty())
        {
            throw new Exception("Package not found");
        }
        return travelPackage.get();
    }
    public Destinations getDestinationById(Long destinationID)throws Exception
    {
        Optional<Destinations> destinations=destinationsRepo.findById(destinationID);
        if(destinations.isEmpty())
        {
            throw new Exception("Activity not found");
        }
        return destinations.get();
    }
    public Activities getActivityById(Long activityID)throws Exception
    {
        Optional<Activities> activities=activitiesRepo.findById(activityID);
        if(activities.isEmpty())
        {
            throw new Exception("Activity not found");
        }
        return activities.get();
    }
    public Passenger getPassengerById(Long passengerID)throws Exception
    {
        Optional<Passenger> passenger=passengerRepo.findById(passengerID);
        if(passenger.isEmpty())
        {
            throw new Exception("Activity not found");
        }
        return passenger.get();
    }
    public ItineraryResponse getItineraryOfPackage(Long packageID)throws Exception
    {
        TravelPackage travelPackage=getPackgeByID(packageID);
        ItineraryResponse response=new ItineraryResponse();
        response.setPackageName(travelPackage.getName());
        response.setDestination(travelPackage.getItinerary());
        return response;
    }
    public PassengerResponse getAllPassengerOfPackage(long packageID) throws Exception {

        TravelPackage travelPackageInfo=getPackgeByID(packageID);

        List<Passenger> passengers=travelPackageInfo.getPassengers();

        PassengerResponse response=new PassengerResponse();
        response.setPackageName(travelPackageInfo.getName());
        response.setCapacity(travelPackageInfo.getCapacity());
        response.setPassengersEnrolled(passengers.size());
        response.setPassengerList(passengers);
        return response;
    }
    public List<AllPassengerResponse> getAllPassengers()
    {
        List<AllPassengerResponse> allPassengerList=new ArrayList<>();

        List<Passenger> passengerList=passengerRepo.findAll();
        for(Passenger passenger:passengerList)
        {
            AllPassengerResponse resp=new AllPassengerResponse();
            resp.setPassengerID(passenger.getId());
            resp.setPassengerName(passenger.getName());
            resp.setBalance(passenger.getBalance());
            for(Activities activities:passenger.getActivities())
            {
                //Activities activity=new Activities();
                Destinations destination=destinationsRepo.findByActivitiesId(activities.getId());
                Activity act=new Activity();

                //act.setActivityName(activities.getDestination().getDestinationName());
                act.setActivityName(activities.getActivityName());
                act.setDestinationName(destination.getDestinationName());
                if(passenger.getSubscriptionType().equals(SubscriptionType.STANDARD))
                    act.setAmountPaid(activities.getCost());
                if(passenger.getSubscriptionType().equals(SubscriptionType.GOLD))
                    act.setAmountPaid(activities.getCost()*0.9);
                if(passenger.getSubscriptionType().equals(SubscriptionType.PREMIUM))
                    act.setAmountPaid(0);

                resp.getActivityList().add(act);

            }
            allPassengerList.add(resp);
        }
        return allPassengerList;
    }
    public List<Activities> searchForAvailableActivities() throws Exception {
        List<Activities> activities=activitiesRepo.searchAvailableActivities();
        if(activities.isEmpty())
            throw new Exception("No activities with available slots");
        return activities;
    }

    public Passenger addBalance(double amount,Long passengerID) throws Exception {
        Passenger passenger=getPassengerById(passengerID);
        passenger.setBalance(passenger.getBalance()+amount);
        return passengerRepo.save(passenger);
    }
}
