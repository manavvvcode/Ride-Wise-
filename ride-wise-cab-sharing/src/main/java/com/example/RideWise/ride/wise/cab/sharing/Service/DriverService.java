package com.example.RideWise.ride.wise.cab.sharing.Service;

import com.example.RideWise.ride.wise.cab.sharing.Dto.DeletedEntity;
import com.example.RideWise.ride.wise.cab.sharing.Dto.DriverDetailsDto;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Driver;
import com.example.RideWise.ride.wise.cab.sharing.Entity.Rider;
import com.example.RideWise.ride.wise.cab.sharing.Entity.User;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.DriverNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderAlreadyExistsException;
import com.example.RideWise.ride.wise.cab.sharing.Exceptions.RiderNotFoundException;
import com.example.RideWise.ride.wise.cab.sharing.Repository.DriverRepository;
import com.example.RideWise.ride.wise.cab.sharing.Repository.RiderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public DriverDetailsDto getDriverInfo(User customUser) {
        Driver driver = driverRepository.findByUser(customUser);
        if (driver.getRides().isEmpty()) {
            return DriverDetailsDto.builder().id(driver.getId()).FirstName(driver.getFirstName()).LastName(driver.getLastName()).AvailabilityStatus(driver.isAvailableStatus()).vehicleType(driver.getVehicleType()).totalRidesCompleted(0).rides(driver.getRides()).build();
        }
        return DriverDetailsDto.builder().id(driver.getId()).FirstName(driver.getFirstName()).LastName(driver.getLastName()).AvailabilityStatus(driver.isAvailableStatus()).vehicleType(driver.getVehicleType()).totalRidesCompleted(driver.getTotalRidesCompleted()).rides(driver.getRides()).build();
    }

//    @Transactional
//    public Driver createNewDriver(Driver driver) throws DriverAlreadyExistsException {
//        if (!driverRepository.existsByEmail(driver.getEmail())) {
//            driverRepository.save(driver);
//        }
//        throw new DriverAlreadyExistsException("Rider already exists!");
//    }

    @Transactional
    public String deleteDriver(User customUser){
        Driver driver = driverRepository.findByUser(customUser);
        driverRepository.delete(driver);
        return "Driver deleted successfully";
    }
}
