package com.example.RideWise.ride.wise.cab.sharing.Controller;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RequestRideDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Service.RideService;
import com.example.RideWise.ride.wise.cab.sharing.Service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/ride")
public class RideController {

    @Autowired
    private RideService rideService;

    //
//    @GetMapping
//    public ResponseEntity<List<Ride>> getAllRides(){
//        return ResponseEntity.status(200).body(rideService.getAllRides());
//    }
//
//    @GetMapping(path = "/{rideId}")
//    public ResponseEntity<Rider> getRiderById(@PathVariable(name = "rideId")Long id) throws RideNotFoundException {
//        return ResponseEntity.status(200).body(rideService.getRideById(id));
//    }
//
    @PostMapping("/request")
    public ResponseEntity<Ride> requestNewRide(@RequestBody RequestRideDto ride, @AuthenticationPrincipal User customUser) throws Exception {
        return ResponseEntity.status(200).body(rideService.requestNewRide(ride, customUser));
    }

    @PostMapping("/start/{rideId}")
    public ResponseEntity<Ride> startRide(@PathVariable Long rideId, @AuthenticationPrincipal User customUser) throws Exception {
        return ResponseEntity.status(200).body(rideService.startRide(rideId, customUser));
    }

//
//    @DeleteMapping(path = "/{riderId}")
//    public ResponseEntity<DeletedEntity<?>> deleteRider(@PathVariable(value = "riderId")Long id) throws RiderNotFoundException {
//        return ResponseEntity.status(200).body(riderService.deleteRider(id));
//    }

}
