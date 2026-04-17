package com.example.RideWise.ride.wise.cab.sharing.Strategy;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;

public interface FareCalculationStrategy {
    Double calculateFare(Ride ride);
}
