package com.example.TravelAgency.Service;

import com.example.TravelAgency.Entity.Activities;
import com.example.TravelAgency.Entity.Destinations;
import com.example.TravelAgency.Entity.TravelPackage;
import com.example.TravelAgency.Repository.ActivitiesRepo;
import com.example.TravelAgency.Repository.DestinationsRepo;
import com.example.TravelAgency.Repository.TravelPackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TravelAdminService {
    @Autowired
    private TravelPackageRepo travelPackageRepo;

    @Autowired
    private DestinationsRepo destinationsRepo;

    @Autowired
    private ActivitiesRepo activitiesRepo;

    public TravelPackage createPackage(TravelPackage req)
    {
        TravelPackage travelPackage=new TravelPackage();
        travelPackage.setName(req.getName());
        travelPackage.setCapacity(req.getCapacity());

//        List<Destinations> destinationList=new ArrayList<>();
//        for(Destinations destination:req.getItinerary())
//        {
//            Destinations destinations=new Destinations();
//            destinations.setDestinationName(destination.getDestinationName());
//
//            List<Activities> activitiesList=new ArrayList<>();
//            for(Activities activities:destination.getActivities())
//            {
//                Activities activity=new Activities();
//                activity.setActivityName(activities.getActivityName());
//                activity.setDescription(activities.getDescription());
//                activity.setCapacity(activities.getCapacity());
//                activity.setAvailable(activities.getAvailable());
//                activity.setCost(activities.getCost());
//
//                //Activities savedActivity=activitiesRepo.save(activity);
//                activitiesList.add(activity);
//            }
//            destinations.setActivities(activitiesList);
//            //Destinations savedDestination=destinationsRepo.save(destinations);
//            destinationList.add(destinations);
//
//        }
 //       travelPackage.setItinerary(destinationList);

        return travelPackageRepo.save(travelPackage);
    }

    public Destinations addDestinationToPackage(Destinations req,Long packageID) throws Exception {

        Optional<TravelPackage> travelPackage=travelPackageRepo.findById(packageID);
        if(travelPackage.isEmpty())
        {
            throw new Exception("Travel Package not found");
        }
        TravelPackage savedTravelPackage=travelPackage.get();

        Destinations destinations=new Destinations();
        destinations.setDestinationName(req.getDestinationName());
        destinations.setTravelPackage(savedTravelPackage);
//        List<Activities> activitiesList=new ArrayList<>();
//        for(Activities activities:req.getActivities())
//        {
//            Activities activity=new Activities();
//            activity.setActivityName(activities.getActivityName());
//            activity.setDescription(activities.getDescription());
//            activity.setCapacity(activities.getCapacity());
//            activity.setAvailable(activities.getAvailable());
//            activity.setCost(activities.getCost());
//
//            Activities savedActivity=activitiesRepo.save(activity);
//            activitiesList.add(savedActivity);
//        }
//        destinations.setActivities(activitiesList);
//        Destinations savedDestination=destinationsRepo.save(destinations);

        return destinationsRepo.save(destinations);
    }
    public Activities addActivityToPackage(Long destinationID,Activities req)throws Exception
    {

        Optional<Destinations> destinations=destinationsRepo.findById(destinationID);
        if(destinations.isEmpty())
        {
            throw new Exception("Travel Package not found");
        }
        Destinations savedDestinations=destinations.get();

        Activities activity=new Activities();
        activity.setActivityName(req.getActivityName());
        activity.setDescription(req.getDescription());
        activity.setCapacity(req.getCapacity());
        activity.setAvailable(req.getAvailable());
        activity.setCost(req.getCost());
        activity.setDestination(savedDestinations);

        return activitiesRepo.save(activity);
    }
}
