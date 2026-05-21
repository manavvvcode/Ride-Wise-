package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.FareReceiptDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RequestRideDto;
import com.example.RideWise.ride.wise.cab.sharing.Dto.RideDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.*;
import com.example.RideWise.ride.wise.cab.sharing.Enum.RideStatus;
import com.example.RideWise.ride.wise.cab.sharing.Enum.VehicleType;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.AlreadyOngoingRideException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.InsufficientFundsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.HelperMethods;
import com.example.RideWise.ride.wise.cab.sharing.Repository.*;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.FareCalculationStrategy;
import com.example.RideWise.ride.wise.cab.sharing.Strategy.RideAllocationStrategyInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final FareReceiptRepository fareReceiptRepository;

    @Transactional
    public RideDetailsDto requestNewRide(RequestRideDto ride, User customUser) throws Exception, AlreadyOngoingRideException {
        Rider rider = riderRepository.findByUser(customUser).orElseThrow(() -> new RiderNotFoundException("rider with email " + customUser.getEmail() + " not found"));
        for (Ride x : rider.getRides()) {
            if (x.getStatus().equals(RideStatus.ONGOING)) {
                throw new AlreadyOngoingRideException("cant book a new ride at this moment! Finish your ride with " + x.getDriver().getFirstName() + " to destination " + x.getDestinationLocation() + " first!");
            }
        }
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
        Double walletBalance = customUser.getUserWallet().getBalance();
        if (ride.getFare() > walletBalance) {
            throw new InsufficientFundsException("you dont have sufficient credits to start this ride!. your current balance is " + walletBalance + " . required credit for this ride is " + ride.getFare() + ". Add more credits now to continue this ride!");
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

    @Transactional
    public FareReceiptDto endRide(Long rideId, User customUser) throws Exception {
        Ride ride = rideRepository.findByIdAndRider_User(rideId, customUser)
                .orElseThrow(() -> new RiderNotFoundException("Ride not found or does not belong to this user"));
        if (ride.getStatus() != RideStatus.ONGOING) {
            throw new Exception("Ride cannot be ended now. current ride status is : " + ride.getStatus());
        }
        ride.setStatus(RideStatus.COMPLETED);
        ride.getDriver().setAvailableStatus(true);
        ride.getDriver().setTotalRidesCompleted(ride.getDriver().getTotalRidesCompleted() + 1);
        FareReceipt fareReceipt = FareReceipt.builder()
                .createdAt(LocalDateTime.now())
                .amount(ride.getFare())
                .ride(ride)
                .build();
        fareReceiptRepository.save(fareReceipt);
        return FareReceiptDto.builder()
                .id(fareReceipt.getId())
                .amount(fareReceipt.getAmount())
                .createdAt(fareReceipt.getCreatedAt())
                .driverName(ride.getDriver().getFirstName())
                .pickupLocation(ride.getPickupLocation())
                .destinationLocation(ride.getDestinationLocation())
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
