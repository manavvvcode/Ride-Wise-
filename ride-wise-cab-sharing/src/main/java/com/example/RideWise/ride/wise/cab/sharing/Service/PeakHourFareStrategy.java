package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.BaseFareStrategy;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.FareCalculationStrategy;
import org.springframework.stereotype.Component;

@Component("peakHourFare")
public class PeakHourFareStrategy extends BaseFareStrategy {
    private static final Double peak_hour_multiplier = 1.5;

    @Override
    public Double calculateFare(Ride ride) {
        return peak_hour_multiplier * calculateBaseFare(ride);
    }
}
