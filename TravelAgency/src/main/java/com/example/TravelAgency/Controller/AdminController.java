package com.example.TravelAgency.Controller;


import com.example.TravelAgency.Entity.Activities;
import com.example.TravelAgency.Entity.Destinations;
import com.example.TravelAgency.Entity.TravelPackage;
import com.example.TravelAgency.Service.TravelAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/travelagency")
public class AdminController {

    @Autowired
    private TravelAdminService travelAdminService;

    @PostMapping("/package")
    public ResponseEntity<TravelPackage> createPackage(@RequestBody TravelPackage req)throws Exception
    {
        TravelPackage travelPackage=travelAdminService.createPackage(req);
        return new ResponseEntity<>(travelPackage, HttpStatus.CREATED);
    }

    @PostMapping("/destination/{packageID}")
    public ResponseEntity<Destinations> addDestinationToPackage(@RequestBody Destinations req,
                                                                 @PathVariable Long packageID)throws Exception
    {
        Destinations destinations=travelAdminService.addDestinationToPackage(req,packageID);
        return new ResponseEntity<>(destinations, HttpStatus.CREATED);
    }

    @PostMapping("/activity/{destinationID}")
    public ResponseEntity<Activities> addActivity(@RequestBody Activities req,
                                                                @PathVariable Long destinationID)throws Exception
    {
        Activities activities=travelAdminService.addActivityToPackage(destinationID,req);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

}
