package com.example.RideWise.ride.wise.cab.sharing.Controller;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping
    public ResponseEntity<List<Driver>> getALlDrivers(){
        return driverService.getAllDrivers();
    }
}
