package com.example.RideWise.ride.wise.cab.sharing.Strategy;

import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;
import com.example.RideWise.ride.wise.cab.sharing.HelperMethods;

import java.util.Map;

public abstract class BaseFareStrategy implements FareCalculationStrategy {
    protected static final Map<VehicleType, Double> baseFareMapByVehicle = Map.of(
            VehicleType.CAR, 15.0, VehicleType.AUTO, 12.0, VehicleType.BIKE, 10.0
    );
    protected static final Map<VehicleType, Double> ratePerKmByVehicle = Map.of(
            VehicleType.CAR, 12.0, VehicleType.AUTO, 8.0, VehicleType.BIKE, 5.0
    );

    protected Double calculateBaseFare(Ride ride) {
        VehicleType type = ride.getDriver().getVehicleType();
        Double baseFare = baseFareMapByVehicle.get(type);
        Double ratePerKm = ratePerKmByVehicle.get(type);
        Double distance = HelperMethods.calculateDistance(ride.getPickupLocation(), ride.getDestinationLocation());
        return baseFare + (ratePerKm * distance);
    }
}
//    public abstract Double calculateFare(Ride ride);
//}
