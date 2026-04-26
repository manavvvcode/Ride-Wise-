package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.RequestRideDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RideDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Ride;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Enum.RideStatus;
import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
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
import java.util.Optional;

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
    public RideDetailsDto requestNewRide(RequestRideDto ride, User customUser) throws Exception {
        Rider rider = riderRepository.findByUser(customUser).orElseThrow(() -> new RiderNotFoundException("rider with email " + customUser.getEmail() + " not found"));
        List<Driver> driverList = driverRepository.findAll();
        RideAllocationStrategyInterface rideStrategy = strategySelector.getDriverStrategy(driverList);
        FareCalculationStrategy fareStrategy = strategySelector.getFareStrategy();
        Double distance = HelperMethods.calculateDistance(ride.getPickupLocation(), ride.getDestinationLocation());
        Driver driver = rideStrategy.assignDriver(driverList, ride.getPickupLocation(), ride.getTypeOfVehicle(), customUser.getEmail());
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
        rideRepository.save(createdRide);
        return RideDetailsDto.builder()
                .id(createdRide.getId())
                .riderName(createdRide.getRider().getFirstName())
                .driverName(createdRide.getDriver().getFirstName())
                .fare(createdRide.getFare())
                .pickup(createdRide.getPickupLocation())
                .drop(createdRide.getDestinationLocation())
                .distance(createdRide.getDistance())
                .build();
    }

    @Transactional
    public RideDetailsDto startRide(Long rideId, User customUser) throws Exception {
        Ride ride = rideRepository.findByIdAndRider_User(rideId, customUser)
                .orElseThrow(() -> new Exception("Ride not found or does not belong to this user"));
        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new Exception("Ride cannot be started, current status: " + ride.getStatus());
        }
        ride.setStatus(RideStatus.ONGOING);
        rideRepository.save(ride);
        return RideDetailsDto.builder()
                .id(ride.getId())
                .riderName(ride.getRider().getFirstName())
                .driverName(ride.getDriver().getFirstName())
                .fare(ride.getFare())
                .pickup(ride.getPickupLocation())
                .drop(ride.getDestinationLocation())
                .distance(ride.getDistance())
                .build();

    }

    @Transactional
    public RideDetailsDto cancelRide(Long rideId, User customUser) throws Exception {
        Ride ride = rideRepository.findByIdAndRider_User(rideId, customUser)
                .orElseThrow(() -> new Exception("Ride not found or does not belong to this user"));
        if (ride.getStatus() == RideStatus.COMPLETED) {
            throw new Exception("Ride already completed, cannot cancel!");
        }
        if (ride.getStatus() == RideStatus.CANCELLED) {
            throw new Exception("Ride is already cancelled!");
        }
        ride.setStatus(RideStatus.CANCELLED);
        ride.getDriver().setAvailableStatus(true);
        return RideDetailsDto.builder()
                .id(ride.getId())
                .riderName(ride.getRider().getFirstName())
                .driverName(ride.getDriver().getFirstName())
                .fare(ride.getFare())
                .pickup(ride.getPickupLocation())
                .drop(ride.getDestinationLocation())
                .distance(ride.getDistance())
                .build();
    }


//    public List<RideDetailsDto> getAllRides(User customUser) throws Exception {
//        List<Ride> ridesList = rideRepository.findAllByRiderByUser(customUser);
//        if (!ridesList.isEmpty()) {
//            return ridesList;
//        }
//        throw new Exception("No rides yet");
//    }
}
