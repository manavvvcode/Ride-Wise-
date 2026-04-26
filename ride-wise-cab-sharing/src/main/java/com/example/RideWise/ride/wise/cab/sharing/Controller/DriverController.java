package com.example.RideWise.ride.wise.cab.sharing.Controller;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.DriverDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Location;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/driver")
@RequiredArgsConstructor
public class DriverController {


    private final DriverService driverService;


    @GetMapping(path = "/me")
    public ResponseEntity<DriverDetailsDto> getDriverInfo(@AuthenticationPrincipal User customUser) throws Exception {
        return ResponseEntity.ok(driverService.getDriverInfo(customUser));
    }

    @PatchMapping
    public ResponseEntity<Driver> updateDriverLocation(@RequestBody Location currentLocation, @AuthenticationPrincipal User customUser) throws Exception {
        return ResponseEntity.status(200).body(driverService.updateLocation(currentLocation, customUser));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteDriver(@AuthenticationPrincipal User customUser) throws Exception {
        return ResponseEntity.status(200).body(driverService.deleteDriver(customUser));
    }
}
