package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Location;
import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.RideAllocationStrategyInterface;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component("LeastActiveDriver")
public class LeastActiveDriverStrategy implements RideAllocationStrategyInterface {
    @Override
    public Driver assignDriver(List<Driver> driverList, Location pickup, VehicleType type, String email) throws Exception {
        return driverList.stream()
                .filter(driver -> !driver.getUser().getEmail().equals(email))
                .filter(Driver::isAvailableStatus)
                .filter(driver -> driver.getVehicleType() == type)
                .min(Comparator.comparingInt(Driver::getTotalRidesCompleted))
                .orElseThrow(() -> new Exception("could not assign a driver at this time!,try again later"));
    }
}
