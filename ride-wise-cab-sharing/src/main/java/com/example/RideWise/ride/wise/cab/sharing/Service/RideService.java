package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.RequestRideDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Enum.RideStatus;
import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;
import com.example.RideWise.ride.wise.cab.sharing.HelperMethods;
import com.example.RideWise.ride.wise.cab.sharing.Repository.DriverRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RideRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RiderRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.UserRepository;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.FareCalculationStrategy;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.RideAllocationStrategyInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

//import static jdk.internal.classfile.impl.DirectCodeBuilder.build;

@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepository rideRepository;
   // private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final StrategySelector strategySelector;

    @Transactional
    public Ride requestNewRide(RequestRideDto ride, User customUser) throws Exception {
        try {
            Rider rider = riderRepository.findByUser(customUser);
            List<Driver> driverList = driverRepository.findAll();
            RideAllocationStrategyInterface rideStrategy = strategySelector.getDriverStrategy(driverList);
            FareCalculationStrategy fareStrategy = strategySelector.getFareStrategy();
            Double distance = HelperMethods.calculateDistance(ride.getPickupLocation(), ride.getDestinationLocation());
            Driver driver = rideStrategy.assignDriver(driverList, ride.getPickupLocation(), ride.getTypeOfVehicle());
            driver.setAvailableStatus(false);
            Ride createdRide = Ride.builder()
                    .driver(driver)
                    .rider(rider)
                    .destinationLocation(ride.getDestinationLocation())
                    .pickupLocation(ride.getPickupLocation())
                    .distance(distance)
                    .status(RideStatus.ASSIGNED)
                    .build();
            createdRide.setFare(fareStrategy.calculateFare(createdRide));
            return rideRepository.save(createdRide);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Ride startRide(Long rideId, User customUser) throws Exception {
        try {
            List<Ride> rideToCheck = rideRepository.findAllByRiderByUser(customUser);
            Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new Exception("ride not found"));
            if (rideToCheck.contains(ride)) {
                ride.setStatus(RideStatus.ONGOING);
            }
            return ride;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
