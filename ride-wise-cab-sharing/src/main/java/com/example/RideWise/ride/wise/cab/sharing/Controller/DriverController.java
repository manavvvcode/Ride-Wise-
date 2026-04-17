package com.example.RideWise.ride.wise.cab.sharing.Controller;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.DriverDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

//    @GetMapping
//    public ResponseEntity<List<Driver>> getALlDrivers() {
//        return ResponseEntity.ok(driverService.getAllDrivers());
//    }

    @GetMapping(path = "/me")
    public ResponseEntity<DriverDetailsDto> getDriverInfo(@AuthenticationPrincipal User customUser) {
        return ResponseEntity.ok(driverService.getDriverInfo(customUser));
    }

//    @PostMapping
//    public ResponseEntity<Driver> createNewRider(@RequestBody Driver driver) throws DriverAlreadyExistsException {
//        return ResponseEntity.status(200).body(driverService.createNewDriver(driver));
//    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteRider(@AuthenticationPrincipal User customUser) {
        return ResponseEntity.status(200).body(driverService.deleteDriver(customUser));
    }
}
