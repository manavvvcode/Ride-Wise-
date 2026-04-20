package com.example.RideWise.ride.wise.cab.sharing;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Location;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelperMethods {

    //Haversine formula to calculate distance between coordinates
    public static double calculateDistance(Location loc1, Location loc2) {
        final int R = 6371;
        double latDistance = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double lonDistance = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(loc1.getLatitude()))
                * Math.cos(Math.toRadians(loc2.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static List<Driver> returnDriverByVehicleType(List<Driver> driverList, VehicleType type) {
        return driverList.stream()
                .filter(driver -> driver.getVehicleType() == type)
                .collect(Collectors.toList());
    }
}
