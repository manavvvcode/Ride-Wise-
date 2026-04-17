package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.BaseFareStrategy;
import org.springframework.stereotype.Component;

@Component("standardFare")
public class StandardFareStrategy extends BaseFareStrategy {


    @Override
    public Double calculateFare(Ride ride) {
        return calculateBaseFare(ride);
    }
}
