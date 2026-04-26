package com.example.RideWise.ride.wise.cab.sharing.Strategy;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Location;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;

import java.util.List;


public interface RideAllocationStrategyInterface {
    Driver assignDriver(List<Driver> driverList, Location pickup, VehicleType type,String email) throws Exception;
}
