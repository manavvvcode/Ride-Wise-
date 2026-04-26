package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.DriverDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Location;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Enum.Role;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Repository.DriverRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RiderRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public DriverDetailsDto getDriverInfo(User customUser) throws DriverNotFoundException {
        Driver driver = driverRepository.findByUser(customUser).orElseThrow(() -> new DriverNotFoundException("driver with email " + customUser.getEmail() + " not found!"));
        if (driver.getRides().isEmpty()) {
            return DriverDetailsDto
                    .builder()
                    .id(driver.getId())
                    .FirstName(driver.getFirstName())
                    .LastName(driver.getLastName())
                    .AvailabilityStatus(driver.isAvailableStatus())
                    .vehicleType(driver.getVehicleType())
                    .totalRidesCompleted(0)
                    .rides(null)
                    .memberSince(driver.getMemberSince().getYear())
                    .build();
        }
        return DriverDetailsDto.builder().id(driver.getId()).FirstName(driver.getFirstName()).LastName(driver.getLastName()).AvailabilityStatus(driver.isAvailableStatus()).vehicleType(driver.getVehicleType()).totalRidesCompleted(driver.getTotalRidesCompleted()).rides(driver.getRides()).memberSince(driver.getMemberSince().getYear()).build();

    }

    @Transactional
    public String deleteDriver(User customUser) throws DriverNotFoundException {
        Driver driver = driverRepository.findByUser(customUser).orElseThrow(() -> new DriverNotFoundException("driver with email " + customUser.getEmail() + " not found!"));
        User user = driver.getUser();
        if (user.getRole().contains(Role.RIDER)) {
            user.getRole().remove(Role.DRIVER);
            userRepository.save(user);
            driverRepository.delete(driver);
            return "Driver account deleted successfully!";
        }
        driverRepository.delete(driver);
        userRepository.delete(user);
        return "Driver account deleted successfully!";
    }

    @Transactional
    public Driver updateLocation(Location currentLocation, User customUser) throws DriverNotFoundException {
        Driver optionalDriver = driverRepository.findByUser(customUser).orElseThrow(() -> new DriverNotFoundException("driver with email " + customUser.getEmail() + " not found!"));
        optionalDriver.setCurrentLocation(currentLocation);
        //not needed the driverRepo.save method since used transactional annotation
        return driverRepository.save(optionalDriver);
    }
}
