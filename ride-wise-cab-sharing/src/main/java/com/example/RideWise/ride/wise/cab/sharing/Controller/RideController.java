package com.example.RideWise.ride.wise.cab.sharing.Controller;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RequestRideDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RideDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Service.RideService;
import com.example.RideWise.ride.wise.cab.sharing.Service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/ride")
public class RideController {

    @Autowired
    private RideService rideService;


    @PreAuthorize("hasRole('RIDER')")
    @PostMapping("/request")
    public ResponseEntity<RideDetailsDto> requestNewRide(@RequestBody RequestRideDto ride, @AuthenticationPrincipal User customUser) throws Exception {
        return ResponseEntity.status(200).body(rideService.requestNewRide(ride, customUser));
    }


    @PostMapping("/start/{rideId}")
    public ResponseEntity<RideDetailsDto> startRide(@PathVariable Long rideId, @AuthenticationPrincipal User customUser) throws Exception {
        return ResponseEntity.status(200).body(rideService.startRide(rideId, customUser));
    }

    @PostMapping("/cancel/{rideId}")
    public ResponseEntity<RideDetailsDto> cancelRide(@PathVariable Long rideId, @AuthenticationPrincipal User customUser) throws Exception {
        return ResponseEntity.status(200).body(rideService.cancelRide(rideId, customUser));
    }


}
